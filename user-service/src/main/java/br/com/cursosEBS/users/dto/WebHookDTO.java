package br.com.cursosEBS.users.dto;

import java.util.Date;

public record WebHookDTO(
        Long id,
        Date date_created,
        String status
) {
}
