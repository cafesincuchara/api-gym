package com.dev.gym.memberships;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberRegisteredEvent(
        UUID memberId,
        String email,
        String planName,
        BigDecimal price
) {}