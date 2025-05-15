package br.com.cursosEBS.courses.dto;

import br.com.cursosEBS.courses.entity.Course;
import br.com.cursosEBS.courses.entity.Lesson;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CoursesDTO(
        Long id,
        String imgURL,
        String title,
        String description,
        BigDecimal price,
        LocalDateTime createdAt,
        Long instructorId,
        Long categoryId,
        List<LessonNoAuthetnicationGetDTO> lessons
) {
    public CoursesDTO(Course course){
        this(course.getId(),
                course.getImgUrl(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.getCreatedAt(),
                course.getInstructorId(),
                course.getCategory().getId(),
                course.getLessons().stream().map(LessonNoAuthetnicationGetDTO::new).toList());
    }
}
