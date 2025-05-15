package br.com.cursosEBS.enrollments.dto;

import br.com.cursosEBS.enrollments.entity.Enrollment;
import br.com.cursosEBS.enrollments.http.dto.CoursesDTO;
import br.com.cursosEBS.enrollments.http.dto.UsersDTO;

import java.time.LocalDateTime;

public record EnrollmentGetDTO(
        Long id,
        UsersDTO user,
        CoursesDTO course,
        LocalDateTime enrollmentDate
) {
    public EnrollmentGetDTO(Enrollment enrollment, UsersDTO user, CoursesDTO course) {
        this(enrollment.getId(), user, course,
                enrollment.getEnrollmentDate());
    }
}
