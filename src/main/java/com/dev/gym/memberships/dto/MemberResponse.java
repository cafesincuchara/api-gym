package com.dev.gym.memberships.dto;

import com.dev.gym.memberships.domain.PlanType;
import java.util.UUID;

public record MemberResponse(
        UUID id,
        String name,
        String email,
        PlanType planType,
        boolean active
) {}