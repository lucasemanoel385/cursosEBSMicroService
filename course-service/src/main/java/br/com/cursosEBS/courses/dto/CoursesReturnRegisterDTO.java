package br.com.cursosEBS.courses.dto;

import br.com.cursosEBS.courses.entity.Course;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CoursesReturnRegisterDTO(
        Long id,
        String imgURL,
        String title,
        String description,
        LocalDateTime createdAt,
        Long instructorId,
        Long categoryId
) {
    public CoursesReturnRegisterDTO(Course course){
        this(course.getId(),
                course.getImgUrl(),
                course.getTitle(),
                course.getDescription(),
                course.getCreatedAt(),
                course.getInstructorId(),
                course.getCategory().getId());
    }
}
