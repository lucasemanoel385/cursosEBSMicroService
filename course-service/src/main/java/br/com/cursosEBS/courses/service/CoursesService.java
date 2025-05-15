package br.com.cursosEBS.courses.service;

import br.com.cursosEBS.courses.dto.CategoryDTO;
import br.com.cursosEBS.courses.dto.CoursesDTO;
import br.com.cursosEBS.courses.dto.PageDTO;
import br.com.cursosEBS.courses.entity.Category;
import br.com.cursosEBS.courses.entity.Course;
import br.com.cursosEBS.courses.repository.CoursesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository repository;

    @Autowired
    private GumletService gumletService;

    public CoursesDTO registerCourses(CoursesDTO dto) {

        var courses = new Course(dto, new Category(dto.categoryId()));

        var response = gumletService.createPlaylist(courses.getTitle());

        courses.setPlaylistId(response.getId());

        repository.save(courses);
        return new CoursesDTO(courses);

    }

    public PageDTO<CoursesDTO> listCourses(Pageable pageable, String search, String category) {

        if (search != null && category != null) {
            return new PageDTO<>(repository.findAllLikeSearchAndCategory(pageable, search, category).map(CoursesDTO::new));
        } else if (search != null) {
            return new PageDTO<>(repository.findAllLikeSearch(pageable, search).map(CoursesDTO::new));
        } else if(category != null) {
            return new PageDTO<>(repository.findAllLikeCategory(pageable, category).map(CoursesDTO::new));
        } else {
            return new PageDTO<>(repository.findAll(pageable).map(CoursesDTO::new));
        }


    }

    public CoursesDTO findCourseById(Long id) {

        return new CoursesDTO(repository.findById(id).orElseThrow(EntityNotFoundException::new));

    }

    public CoursesDTO updateCourse(Long id, CoursesDTO dto) {

        var course = new Course(dto, new Category(dto.categoryId()));
        course.setId(id);
        repository.save(course);
        return new CoursesDTO(course);
    }

    public void deleteCourseById(Long id) {
        repository.deleteById(id);
    }

    public PageDTO<CoursesDTO> listCoursesByParam(Pageable pageable, String search) {
        return new PageDTO<>(repository.findAllLikeSearch(pageable, search).map(CoursesDTO::new));
    }
}
