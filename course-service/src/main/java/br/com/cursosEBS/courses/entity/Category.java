package br.com.cursosEBS.courses.entity;

import br.com.cursosEBS.courses.dto.CategoryDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    public Category(CategoryDTO dto) {
        this.name = dto.name();
    }

    public Category(Long id) {
        this.id = id;
    }
}
