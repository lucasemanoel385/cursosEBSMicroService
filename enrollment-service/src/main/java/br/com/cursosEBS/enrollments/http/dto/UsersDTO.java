package br.com.cursosEBS.enrollments.http.dto;

import br.com.cursosEBS.enrollments.http.enumuser.Role;

import java.time.LocalDateTime;

public record UsersDTO(
        Long id,
        String name,
        String email,
        String password,
        Role role,
        LocalDateTime createdAt
) {
}
