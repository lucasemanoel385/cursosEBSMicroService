package br.com.cursosEBS.payment.http.dto;

import br.com.cursosEBS.payment.entity.StatusPayment;

public record RegisterSubscriptionDTO(
        Long userId,
        StatusPayment status,
        String paidMarketId
) {
}
