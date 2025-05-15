package br.com.cursosEBS.users.service;

import br.com.cursosEBS.users.dto.ChangePasswordDTO;
import br.com.cursosEBS.users.dto.EmailDTO;
import br.com.cursosEBS.users.entity.PasswordResetToken;
import br.com.cursosEBS.users.entity.User;
import br.com.cursosEBS.users.infra.config.messageconfig.MessageSourceConfig;
import br.com.cursosEBS.users.repository.PasswordTokenRepository;
import br.com.cursosEBS.users.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordTokenRepository repositoryPassword;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MessageSourceConfig messages;

    @Value("${spring.url}")
    private String contextPath;

    //Send email with token for change password
    public EmailDTO resetPassword(String username) {
        User user = repository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        return new EmailDTO("Reset Password", token, user);
        //return constructResetTokenEmail(new Locale("pt", "BR"), token, user);
    }

    private void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        repositoryPassword.save(myToken);
    }

    //Update Password
    public String changePassword(ChangePasswordDTO dto) {
        var passwordResetToken = repositoryPassword.findByToken(dto.token());
        var check = checkPasswordResetToken(passwordResetToken);

        if(!dto.password().equals(dto.confirmPassword()) && !check) {
            throw new EntityNotFoundException("Token invalido ou expirado ou senhas nao sao semelhantes");
        }

        User user = new User(passwordResetToken.get().getUser());
        user.setPassword(encoder.encode(dto.confirmPassword()));
        repository.save(user);

        return "Senha atualizada com sucesso";
    }

    private boolean checkPasswordResetToken(Optional<PasswordResetToken> passwordResetToken) {
        return passwordResetToken.isPresent() && !isTokenExpired(passwordResetToken.get());

    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
