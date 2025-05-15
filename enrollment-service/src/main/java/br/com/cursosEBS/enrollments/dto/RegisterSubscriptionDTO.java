package br.com.cursosEBS.enrollments.dto;

import br.com.cursosEBS.enrollments.entity.StatusPayment;
import br.com.cursosEBS.enrollments.entity.Subscription;

import java.time.LocalDateTime;

public record RegisterSubscriptionDTO(
        String id,
        Long userId,
        StatusPayment status,
        LocalDateTime startDate,
        LocalDateTime expirationsDate,
        String paidMarketId
) {
    public RegisterSubscriptionDTO(Subscription subs) {
        this(subs.getId(), subs.getUserId(),subs.getStatus(), subs.getStartDate(),subs.getExpirationDate(), subs.getPaidMarketId());
    }
}
