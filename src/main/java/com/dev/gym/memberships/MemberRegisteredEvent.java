package com.dev.gym.memberships;

import java.util.UUID;

public record MemberRegisteredEvent(
        UUID memberId,
        String email,
        String planName
) {}