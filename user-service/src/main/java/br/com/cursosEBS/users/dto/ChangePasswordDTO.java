package br.com.cursosEBS.users.dto;

public record ChangePasswordDTO(
        String token,
        String password,
        String confirmPassword
) {
}
