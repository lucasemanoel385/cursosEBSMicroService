package br.com.cursosEBS.payment.http.dto;

import br.com.cursosEBS.payment.entity.StatusPayment;

public record UpdateStatusSubscriptionDTO(
        StatusPayment status,
        String subscriptionId
) {
}
