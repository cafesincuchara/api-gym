package com.dev.gym.billing.dto;

import com.dev.gym.billing.domain.Status;

import java.math.BigDecimal;
import java.util.UUID;

public record InvoiceResponse(
        UUID id,
        UUID memberId,
        BigDecimal amount,
        Status status
) {
}
