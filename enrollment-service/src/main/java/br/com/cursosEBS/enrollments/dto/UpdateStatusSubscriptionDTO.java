package br.com.cursosEBS.enrollments.dto;

import br.com.cursosEBS.enrollments.entity.StatusPayment;

public record UpdateStatusSubscriptionDTO(
        StatusPayment status,
        String subscriptionId
) {
}
