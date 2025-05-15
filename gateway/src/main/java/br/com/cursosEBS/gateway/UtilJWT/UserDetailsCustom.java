package br.com.cursosEBS.gateway.UtilJWT;

import com.auth0.jwt.interfaces.DecodedJWT;

public record UserDetailsCustom(
        String username,
        String role,
        String token
) {

    public UserDetailsCustom(DecodedJWT decodedJWT) {
        this(decodedJWT.getSubject(), decodedJWT.getClaim("role").asString(), decodedJWT.getToken());
    }
}
