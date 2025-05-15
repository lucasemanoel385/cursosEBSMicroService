package br.com.cursosEBS.users.repository;

import br.com.cursosEBS.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDetails> findByEmail(String username);

    @Query("SELECT u FROM User u WHERE u.email = :username")
    User findUserByEmail(String username);

    @Query("SELECT u FROM User u JOIN u.profile p WHERE p.name <> :role")
    Page<User> findAllExcludingRole(@Param("role") String role, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.profile p WHERE p.name <> :role and (u.name LIKE :search% or CAST(u.id AS string) = :search%)")
    Page<User> findAllExcludingRoleAndSearch(@Param("role") String role, Pageable pageable,@Param("search") String search);

    @Query("SELECT u FROM User u JOIN u.profile p WHERE p.name = :role AND u.name LIKE :instructor%")
    Page<User> findAllByRole(Pageable pageable, String role, String instructor);

    @Query("SELECT u FROM User u JOIN u.profile p WHERE u.id = :id AND p.name = :role")
    Optional<User> findByIdWithProfileEqualsInstructor(Long id, String role);

    boolean existsByEmail(String email);
}
