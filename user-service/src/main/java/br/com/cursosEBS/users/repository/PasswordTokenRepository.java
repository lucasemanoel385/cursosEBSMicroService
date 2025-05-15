package br.com.cursosEBS.users.repository;

import br.com.cursosEBS.users.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    @Query("SELECT p FROM PasswordResetToken p WHERE p.token = :token")
    Optional<PasswordResetToken> findByToken(String token);
}
