package br.com.cursosEBS.users.dto;

import br.com.cursosEBS.users.enums.StatusPayment;

public record UpdateProfileDTO(
        Long id,
        StatusPayment status
) {
}
