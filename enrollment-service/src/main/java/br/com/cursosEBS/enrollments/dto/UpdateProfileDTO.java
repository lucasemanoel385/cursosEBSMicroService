package br.com.cursosEBS.enrollments.dto;

import br.com.cursosEBS.enrollments.entity.StatusPayment;

public record UpdateProfileDTO(
        Long id,
        StatusPayment status
) {
}
