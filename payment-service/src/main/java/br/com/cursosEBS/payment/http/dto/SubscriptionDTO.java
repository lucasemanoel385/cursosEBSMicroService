package br.com.cursosEBS.payment.http.dto;

import br.com.cursosEBS.payment.entity.StatusPayment;

import java.time.LocalDateTime;

public record SubscriptionDTO(
        String id,
        Long userId,
        StatusPayment status,
        LocalDateTime expirationsDate,
        String paidMarketId
) {
}
