package br.com.rodrigobsjava.dto;

import br.com.rodrigobsjava.domain.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull OrderStatus status
) {
}
