package br.com.cursosEBS.users.dto;

import br.com.cursosEBS.users.entity.Profile;
import br.com.cursosEBS.users.entity.User;

public record ListUserViewDTO(
        Long id,
        String name,
        String email,
        Profile role
) {
    public ListUserViewDTO(User users) {
        this(users.getId(),users.getName(), users.getEmail(), users.getProfile().getFirst());
    }
}
