package br.com.cursosEBS.users.dto;

import org.springframework.data.domain.Page;

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
