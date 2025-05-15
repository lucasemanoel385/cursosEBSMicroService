package br.com.cursosEBS.enrollments.dto;

import br.com.cursosEBS.enrollments.entity.Enrollment;

import java.time.LocalDateTime;

public record EnrollmentDTO(
        Long id,
        Long userId,
        Long courseId,
        LocalDateTime enrollmentDate
) {
    public EnrollmentDTO(Enrollment enrollment){
        this(enrollment.getId(), enrollment.getUserId(), enrollment.getCourseId(),
                enrollment.getEnrollmentDate());
    }
}
