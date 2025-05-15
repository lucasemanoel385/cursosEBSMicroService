package br.com.cursosEBS.courses.dto;


public record LessonPostDTO(
        Long id,
        Long courseId,
        String title,
        String description,
        String videoUrl,
        Integer duration,
        Integer position
) {
}
