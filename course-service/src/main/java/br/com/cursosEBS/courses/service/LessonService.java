package br.com.cursosEBS.courses.service;

import br.com.cursosEBS.courses.dto.GumletUploadResponse;
import br.com.cursosEBS.courses.dto.LessonGetDTO;
import br.com.cursosEBS.courses.dto.LessonPostDTO;
import br.com.cursosEBS.courses.dto.PageDTO;
import br.com.cursosEBS.courses.entity.Course;
import br.com.cursosEBS.courses.entity.Lesson;
import br.com.cursosEBS.courses.repository.CoursesRepository;
import br.com.cursosEBS.courses.repository.LessonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository repository;

    @Autowired
    private GumletService gumletService;

    @Autowired
    private CoursesRepository coursesRepository;

    public Lesson createLessonWithVideo(LessonPostDTO dto, MultipartFile file) throws IOException {
        // Encontra o curso pelo ID
        Course course = coursesRepository.findById(dto.courseId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        // Faz o upload do vídeo e obtém os dados da resposta
        GumletUploadResponse gumletResponse = gumletService.createUploadAsset(dto.title(),  course.getPlaylistId()); // exemplo de collectionId

        // Faz o PUT do vídeo para o Gumlet
        gumletService.uploadVideoToSignedUrl(gumletResponse.getUpload_url(), file);

        // Cria a lesson com os dados retornados
        Lesson lesson = new Lesson(dto);
        lesson.setCourseId(course);
        lesson.setVideoUrl(gumletResponse.getAssetId());

        // Salva a nova lesson no banco de dados
        return repository.save(lesson);
    }


    public LessonGetDTO registerLesson(LessonPostDTO dto) {
        var lesson = new Lesson(dto, new Course(dto.courseId()));
        repository.save(lesson);
        return new LessonGetDTO(lesson);
    }

    public PageDTO<LessonGetDTO> listLessons(Pageable pageable, String search) {

        return new PageDTO<>(search == null || search.isEmpty() ? repository.findAll(pageable).map(LessonGetDTO::new) : repository.findAllLikeSearch(pageable,search).map(LessonGetDTO::new));
    }

    public LessonGetDTO findLessonById(Long id) {
        return new LessonGetDTO(repository.findById(id).orElseThrow(EntityExistsException::new));
    }

    public LessonGetDTO updateLesson(Long id, LessonPostDTO dto) {

        var lesson = new Lesson(dto, new Course(dto.courseId()));
        lesson.setId(id);
        repository.save(lesson);
        return new LessonGetDTO(lesson);
    }

    public void deleteLessonById(Long id) {
        repository.deleteById(id);
    }

    public List<LessonGetDTO> listLessonsByCourseId(Long id) {

        return repository.findByCourseId_Id(id).stream().map(LessonGetDTO::new).toList();

    }
}
