package com.dev.gym.auth.internal;

import com.dev.gym.auth.JwtService;
import com.dev.gym.auth.domain.Role;
import com.dev.gym.auth.dto.AuthResponse;
import com.dev.gym.auth.dto.LoginRequest;
import com.dev.gym.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request){

        var user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getEmail(), user.getRole(), "Registro completo");
    }

    public AuthResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var user = userRepository.findByEmail(request.email()).orElseThrow(()-> new IllegalArgumentException("Usuario no existe"));
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken , user.getEmail(),user.getRole(), "Login exitoso ");
    }
}
