package br.com.cursosEBS.courses.controller;

import br.com.cursosEBS.courses.dto.CoursesDTO;
import br.com.cursosEBS.courses.dto.PageDTO;
import br.com.cursosEBS.courses.service.CoursesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    private CoursesService service;

    @PostMapping
    public ResponseEntity<CoursesDTO> registerCourses(@RequestBody @Valid CoursesDTO dto, UriComponentsBuilder uriBuilder) {

        var courses = service.registerCourses(dto);
        URI uri = uriBuilder.path("/courses/{id}").buildAndExpand(courses.id()).toUri();

        return ResponseEntity.created(uri).body(courses);

    }

    @GetMapping
    public ResponseEntity<PageDTO<CoursesDTO>> listCourses(@PageableDefault(size = 7, page = 0) Pageable pageable, @RequestParam(required = false) String search,
                                                           @RequestParam(required = false) String category) {
        return ResponseEntity.ok(service.listCourses(pageable, search, category));
    }


    @GetMapping("/filter")
    public ResponseEntity<PageDTO<CoursesDTO>> listCoursesByParam(@PageableDefault(size = 100, page = 0) Pageable pageable,
                                                                   @RequestParam String search) {
        return ResponseEntity.ok(service.listCoursesByParam(pageable, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoursesDTO> findCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findCourseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoursesDTO> updateCourse(@PathVariable Long id, @RequestBody CoursesDTO dto) {
        return ResponseEntity.ok(service.updateCourse(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CoursesDTO> deleteCourseById(@PathVariable @NotNull Long id) {
        service.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }

}
