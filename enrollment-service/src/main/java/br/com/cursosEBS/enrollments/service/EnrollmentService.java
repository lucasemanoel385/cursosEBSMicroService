package br.com.cursosEBS.enrollments.service;

import br.com.cursosEBS.enrollments.dto.EnrollmentGetDTO;
import br.com.cursosEBS.enrollments.dto.EnrollmentDTO;
import br.com.cursosEBS.enrollments.entity.Enrollment;
import br.com.cursosEBS.enrollments.http.CourseClient;
import br.com.cursosEBS.enrollments.http.UserClient;
import br.com.cursosEBS.enrollments.repository.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository repository;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private UserClient userClient;

    public EnrollmentDTO registerEnrollment(Long userId, Long courseId) {

        if (Boolean.TRUE.equals(repository.existsEnrollmentWithUserIdAndEnrollId(userId, courseId))) {
            throw new RuntimeException("Ja inscrito");
        }

        var enrollment = new Enrollment(userId, courseId);
        repository.save(enrollment);
        return new EnrollmentDTO(enrollment);
    }

    public int listEnrollment() {

        return repository.findAll().size();
    }

    public EnrollmentGetDTO findEnrollmentById(Long id) {
        var enrollment = repository.findById(id);
        if (!enrollment.isPresent()) {
            throw new EntityNotFoundException();
        }
        var user = userClient.getUserById(enrollment.get().getUserId());
        var course = courseClient.getUserById(enrollment.get().getUserId());
        return new EnrollmentGetDTO(enrollment.get(), user, course);
    }

    /*public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO dto) {

        var enrollment = new Enrollment(dto);
        enrollment.setId(id);
        repository.save(enrollment);
        return new EnrollmentDTO(enrollment);
    }*/

    public void deleteEnrollment(Long id) {
        repository.deleteById(id);
    }

    public boolean findEnrollmentByIdUserAndEnroll(Long userId, Long courseId) {

        return repository.existsEnrollmentWithUserIdAndEnrollId(userId, courseId);

    }
}
