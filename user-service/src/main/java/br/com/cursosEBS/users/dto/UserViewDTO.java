package br.com.cursosEBS.users.dto;

import br.com.cursosEBS.users.entity.Profile;
import br.com.cursosEBS.users.entity.User;

import java.time.LocalDateTime;

public record UserViewDTO(
        Long id,
        String name,
        String email,
        String password,
        Profile role,
        LocalDateTime createdAt
) {
    public UserViewDTO(User users) {
        this(users.getId(),users.getName(), users.getEmail(), users.getPassword(), users.getProfile().getFirst(), users.getCreatedAt());
    }
}
