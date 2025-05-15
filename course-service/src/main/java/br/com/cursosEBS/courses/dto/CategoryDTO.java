package br.com.cursosEBS.courses.dto;

import br.com.cursosEBS.courses.entity.Category;

public record CategoryDTO(
        Long id,
        String name
) {
    public CategoryDTO(Category category){
        this(category.getId(), category.getName());
    }
}
