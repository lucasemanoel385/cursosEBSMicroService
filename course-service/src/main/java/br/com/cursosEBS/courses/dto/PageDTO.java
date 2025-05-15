package br.com.cursosEBS.courses.dto;

import br.com.cursosEBS.courses.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public record PageDTO<T>(
        PaginatorDTO<T> page,
        List<T> content
) {
    public PageDTO(Page<T> page) {
        this(
                new PaginatorDTO<>(page),
                page.getContent()
        );
    }
}
