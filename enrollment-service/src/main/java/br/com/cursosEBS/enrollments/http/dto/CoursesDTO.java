package br.com.cursosEBS.enrollments.http.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CoursesDTO(
        Long id,
        String title,
        String description,
        BigDecimal price,
        LocalDateTime createdAt,
        Long instructorId
) {
}
