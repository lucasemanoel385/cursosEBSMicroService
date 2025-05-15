package br.com.cursosEBS.users.dto;

import br.com.cursosEBS.users.entity.User;

public record InstructorDTO(
        Long id,
        String instructor
) {
    public InstructorDTO(User user) {
        this(user.getId(), user.getName());
    }
}
