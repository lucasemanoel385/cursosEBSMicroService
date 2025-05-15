package br.com.cursosEBS.enrollments.service;

import br.com.cursosEBS.enrollments.dto.RegisterSubscriptionDTO;
import br.com.cursosEBS.enrollments.dto.UpdateProfileDTO;
import br.com.cursosEBS.enrollments.dto.UpdateStatusSubscriptionDTO;
import br.com.cursosEBS.enrollments.entity.StatusPayment;
import br.com.cursosEBS.enrollments.entity.Subscription;
import br.com.cursosEBS.enrollments.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public RegisterSubscriptionDTO registerSubscription(RegisterSubscriptionDTO dto) {

        Subscription subs = new Subscription(dto);
        subs.setStartDate(LocalDateTime.now());
        subscriptionRepository.save(subs);

        if (dto.status().equals(StatusPayment.APPROVED)) {
            subs.setExpirationDate(LocalDateTime.now().plusYears(1L));
            rabbitTemplate.convertAndSend("users.sb", "users.profile", new UpdateProfileDTO(subs.getUserId() , subs.getStatus()));
        }

        return new RegisterSubscriptionDTO(subs);

    }

    public Subscription getSubscriptionId(String id) {

        return subscriptionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteSubscriptionId(String id) {

        subscriptionRepository.deleteById(Long.valueOf(id));
    }

    public void updateStatus(UpdateStatusSubscriptionDTO dto) {

        var subs = subscriptionRepository.findById(dto.subscriptionId()).orElseThrow(EntityNotFoundException::new);
        subs.setStatus(dto.status());

        if (dto.status().equals(StatusPayment.REFUNDED)) {
            subs.setExpirationDate(LocalDateTime.now());
        }

        if (dto.status().equals(StatusPayment.APPROVED)) {
            subs.setExpirationDate(LocalDateTime.now().plusYears(1L));
        }

        rabbitTemplate.convertAndSend("users.sb", "users.profile", new UpdateProfileDTO(subs.getUserId() , subs.getStatus()));

        subscriptionRepository.save(subs);
    }
}
