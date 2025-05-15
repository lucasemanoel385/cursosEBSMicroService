package br.com.cursosEBS.courses.dto;

import org.springframework.data.domain.Page;

public record PaginatorDTO<T>(
        long totalElements,
        int totalPages,
        int pageSize,
        int pageNumber,
        boolean first,
        boolean last
) {
   public PaginatorDTO(Page<T> page) {
      this(
              page.getTotalElements(),
              page.getTotalPages(),
              page.getSize(),
              page.getNumber(),
              page.isFirst(),
              page.isLast()
      );
   }
}
