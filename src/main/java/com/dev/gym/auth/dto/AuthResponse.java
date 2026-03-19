package com.dev.gym.auth.dto;

import com.dev.gym.auth.domain.Role;

public record AuthResponse(
        String token,
        String email,
        Role role,
        String message
) {}