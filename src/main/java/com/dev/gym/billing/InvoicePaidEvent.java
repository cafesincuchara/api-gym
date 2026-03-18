package com.dev.gym.billing;

import java.time.LocalDateTime;
import java.util.UUID;

public record InvoicePaidEvent(UUID invoiceId, String memberEmail, LocalDateTime dateTime) {}