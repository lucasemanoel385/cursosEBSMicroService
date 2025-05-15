package br.com.cursosEBS.users.dto;

import br.com.cursosEBS.users.entity.Profile;

import java.util.Optional;

public record ProfileDTO(
        Long id,
        String name
) {
    public ProfileDTO(Profile profile){
        this(profile.getId(), profile.getName());
    }
}
