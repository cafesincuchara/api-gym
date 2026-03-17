package com.dev.gym.memberships.dto;

import com.dev.gym.memberships.domain.PlanType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotNull PlanType planType // Usa @NotNull para Enums
) {}