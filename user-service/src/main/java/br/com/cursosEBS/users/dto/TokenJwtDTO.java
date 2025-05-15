package br.com.cursosEBS.users.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record TokenJwtDTO(String tokenJWT, Collection<? extends GrantedAuthority> authorities, String name, Long id) {

}
