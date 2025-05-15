package br.com.cursosEBS.payment.repository;

import br.com.cursosEBS.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaidMarketId(String id);
}
