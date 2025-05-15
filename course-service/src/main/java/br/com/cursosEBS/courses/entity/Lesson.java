package br.com.cursosEBS.courses.entity;

import br.com.cursosEBS.courses.dto.LessonPostDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lesson")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course courseId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String videoUrl;

    private Integer duration;

    @NotNull
    private Integer position;

    public Lesson(LessonPostDTO dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.videoUrl = dto.videoUrl();
        this.duration = dto.duration();
        this.position = dto.position();
    }

    public Lesson(LessonPostDTO dto, Course course) {
        this.courseId = course;
        this.title = dto.title();
        this.description = dto.description();
        this.videoUrl = dto.videoUrl();
        this.duration = dto.duration();
        this.position = dto.position();
    }
}
