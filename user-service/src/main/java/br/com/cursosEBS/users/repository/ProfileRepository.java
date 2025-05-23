package br.com.cursosEBS.users.repository;

import br.com.cursosEBS.users.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Page<Profile> findAllByNameNot(Pageable pageable, String name);
}
