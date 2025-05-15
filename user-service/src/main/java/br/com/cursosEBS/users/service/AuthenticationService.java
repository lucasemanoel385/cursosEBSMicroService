package br.com.cursosEBS.users.service;

import br.com.cursosEBS.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder check;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário inválido"));
        return user;
    }

    public UserDetails checkLoginOrPassword(String username, String password) {
        var user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário inválido"));
        if (!check.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("Senha inválida");
        }
        return user;
    }
}
