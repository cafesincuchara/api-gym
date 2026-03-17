package com.dev.gym.billing;

import java.util.UUID;

public record InvoicePaidEvent(UUID invoiceId, String memberEmail) {}