package br.com.rodrigobsjava.dto;

import br.com.rodrigobsjava.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID customerId,
        OrderStatus status,
        BigDecimal amount,
        Instant createdAt
) {
}
