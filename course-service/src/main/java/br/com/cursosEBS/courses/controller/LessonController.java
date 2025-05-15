package br.com.cursosEBS.courses.controller;

import br.com.cursosEBS.courses.dto.LessonGetDTO;
import br.com.cursosEBS.courses.dto.LessonPostDTO;
import br.com.cursosEBS.courses.dto.PageDTO;
import br.com.cursosEBS.courses.entity.Lesson;
import br.com.cursosEBS.courses.service.LessonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService service;

    /*@PostMapping
    public ResponseEntity<LessonGetDTO> registerLesson(@RequestBody @Valid LessonPostDTO dto, UriComponentsBuilder uriBuilder) {

        var lesson = service.registerLesson(dto);
        URI uri = uriBuilder.path("/lesson/{id}").buildAndExpand(lesson.id()).toUri();

        return ResponseEntity.created(uri).body(lesson);
    }*/

    @PostMapping
    public ResponseEntity<Lesson> registerLesson(@RequestPart("file") MultipartFile file, @RequestPart("data") LessonPostDTO data) {

        try {
            var lesson = service.createLessonWithVideo(data, file);
            return ResponseEntity.ok(lesson);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<PageDTO<LessonGetDTO>> listLessons(@PageableDefault(page = 0, size = 7) Pageable pageable, @RequestParam(required = false) String search) {
        return ResponseEntity.ok(service.listLessons(pageable, search));
    }

    @GetMapping("/get-by-course/{id}")
    public ResponseEntity<List<LessonGetDTO>> listLessons(@PathVariable Long id) {
        return ResponseEntity.ok(service.listLessonsByCourseId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonGetDTO> findLessonById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(service.findLessonById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonGetDTO> updateLesson(@PathVariable @NotNull Long id, @RequestBody LessonPostDTO dto) {
        return ResponseEntity.ok(service.updateLesson(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LessonPostDTO> deleteLessonById(@PathVariable @NotNull Long id) {
        service.deleteLessonById(id);
        return ResponseEntity.noContent().build();
    }

}
