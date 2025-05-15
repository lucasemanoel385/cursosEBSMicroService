package br.com.cursosEBS.email.dto;

public record EmailDTO(
        String subject,
        String token,
        User user
) {
}
