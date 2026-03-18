package com.dev.gym.memberships.domain;

import java.math.BigDecimal;

public enum PlanType {
    BASIC(new BigDecimal("5000")),
    PREMIUM(new BigDecimal("7900")),
    VIP(new BigDecimal("15000")),
    DEFAULT(new BigDecimal("2000"));
    private final BigDecimal price;

    PlanType(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static PlanType fromName(String name) {
        if (name == null) return DEFAULT;
        try {
            return PlanType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT;
        }
    }
}