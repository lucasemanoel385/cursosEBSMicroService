package br.com.cursosEBS.courses.entity;

import br.com.cursosEBS.courses.dto.CoursesDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDateTime createdAt;

    private String playlistId;

    @NotNull
    private Long instructorId;

    @OneToMany(mappedBy = "courseId", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Lesson> lessons;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Course(CoursesDTO dto, Category category) {
        this.imgUrl = dto.imgURL();
        this.title = dto.title();
        this.description = dto.description();
        this.createdAt = LocalDateTime.now();
        this.instructorId = dto.instructorId();
        this.category = category;
    }

    public Course(Long id) {
        this.id = id;
    }
}
