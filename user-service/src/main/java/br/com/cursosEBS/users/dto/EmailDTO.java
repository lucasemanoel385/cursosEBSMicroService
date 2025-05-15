package br.com.cursosEBS.users.dto;

import br.com.cursosEBS.users.entity.User;

public record EmailDTO(
        String subject,
        String token,
        User user
) {
}
