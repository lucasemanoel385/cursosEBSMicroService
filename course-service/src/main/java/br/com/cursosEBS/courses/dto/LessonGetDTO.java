package br.com.cursosEBS.courses.dto;

import br.com.cursosEBS.courses.entity.Course;
import br.com.cursosEBS.courses.entity.Lesson;

public record LessonGetDTO(
        Long id,
        String title,
        String description,
        String videoUrl,
        Integer duration,
        Integer position,
        Long courseId,
        String courseName

) {
    public LessonGetDTO(Lesson lesson){
        this(lesson.getId(), lesson.getTitle(), lesson.getDescription(), lesson.getVideoUrl(), lesson.getDuration(),
                lesson.getPosition(),lesson.getCourseId().getId(), lesson.getCourseId().getTitle() );
    }
}
