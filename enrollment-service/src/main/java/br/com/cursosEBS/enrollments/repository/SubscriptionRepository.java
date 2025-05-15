package br.com.cursosEBS.enrollments.repository;

import br.com.cursosEBS.enrollments.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findById(String id);
}
