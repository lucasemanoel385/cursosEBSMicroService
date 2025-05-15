package br.com.cursosEBS.users.dto;

import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;

public record UserRegisterDTO(
        Long id,
        String name,
        @Email(message = "Deve ser um endereço de email")
        String email,
        String password,
        Long role,
        LocalDateTime createdAt
) {
    /*public UserRegisterDTO(User users) {
        this(users.getId(),users.getName(), users.getEmail(), users.getPassword(), users.getProfile().get(0).getName(), users.getCreatedAt());
    }*/
}
