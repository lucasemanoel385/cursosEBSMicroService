package br.com.cursosEBS.enrollments.controller;

import br.com.cursosEBS.enrollments.dto.EnrollmentGetDTO;
import br.com.cursosEBS.enrollments.dto.EnrollmentDTO;
import br.com.cursosEBS.enrollments.service.EnrollmentService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService service;

    @PostMapping("/{userId}/{courseId}")
    public ResponseEntity<?> registerEnrollment(@PathVariable @NotNull Long userId, @PathVariable Long courseId, UriComponentsBuilder uriBuilder){
        var enrollment = service.registerEnrollment(userId, courseId);
        if (enrollment == null) {
            return ResponseEntity.badRequest().body("Matrícula inválida");
        }
        URI uri = uriBuilder.path("/enrollment/{id}").buildAndExpand(enrollment.id()).toUri();
        return ResponseEntity.created(uri).body(enrollment);
    }

    @GetMapping
    public ResponseEntity<Integer> listEnrollment(){
        return  ResponseEntity.ok(service.listEnrollment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentGetDTO>findEnrollmentById(@PathVariable @NotNull Long id){
        return  ResponseEntity.ok(service.findEnrollmentById(id));
    }

    @GetMapping("/check-user-enroll/{userId}/{courseId}")
    public ResponseEntity<Boolean> findEnrollmentById(@PathVariable @NotNull Long userId, @PathVariable Long courseId){
        return  ResponseEntity.ok(service.findEnrollmentByIdUserAndEnroll(userId, courseId));
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO>updateEnrollment(@PathVariable @NotNull Long id, @RequestBody EnrollmentDTO dto) {
        return ResponseEntity.ok(service.updateEnrollment(id, dto));
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<EnrollmentDTO>deleteEnrollment(@PathVariable @NotNull Long id){
        service.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
