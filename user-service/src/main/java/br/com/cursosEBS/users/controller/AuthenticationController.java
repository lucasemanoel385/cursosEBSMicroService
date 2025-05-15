package br.com.cursosEBS.users.controller;

import br.com.cursosEBS.users.dto.AuthenticationDTO;
import br.com.cursosEBS.users.dto.TokenJwtDTO;
import br.com.cursosEBS.users.entity.User;
import br.com.cursosEBS.users.service.AuthenticationService;
import br.com.cursosEBS.users.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService token;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenJwtDTO> login(@RequestBody @Valid AuthenticationDTO dto){

        var user = authenticationService.checkLoginOrPassword(dto.username(), dto.password());

        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = token.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT, user.getAuthorities(), ((User) authentication.getPrincipal()).getName(), ((User) user).getId()));


    }
}
