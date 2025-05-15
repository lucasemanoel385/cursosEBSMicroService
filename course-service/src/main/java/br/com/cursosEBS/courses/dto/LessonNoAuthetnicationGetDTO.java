package br.com.cursosEBS.courses.dto;


import br.com.cursosEBS.courses.entity.Lesson;

public record LessonNoAuthetnicationGetDTO(
        Long id,
        Long courseId,
        String title,
        String description,
        Integer duration,
        Integer position
) {

    public LessonNoAuthetnicationGetDTO(Lesson lesson) {
        this(lesson.getId(), lesson.getCourseId().getId(), lesson.getTitle(), lesson.getDescription(), lesson.getDuration(), lesson.getPosition());
    }
}
