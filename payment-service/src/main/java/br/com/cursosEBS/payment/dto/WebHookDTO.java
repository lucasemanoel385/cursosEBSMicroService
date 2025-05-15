package br.com.cursosEBS.payment.dto;

import java.time.LocalDateTime;

public record WebHookDTO(
        String action,
        String api_version,
        DataDTO data,
        String external_reference,
        LocalDateTime date_created
) {
}
