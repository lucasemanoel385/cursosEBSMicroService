package br.com.cursosEBS.users.dto;

import jakarta.validation.constraints.Email;

public record AuthenticationDTO(
        @Email(message = "Deve ser um endereço de email")
        String username,
        String password
) {
}
