package com.example.auth.service;

import com.example.auth.dto.*;
import com.example.auth.model.*;
import com.example.auth.repository.*;
import com.example.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Map<String, Object> register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) throw new IllegalArgumentException("Email in use");
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.ROLE_USER)
                .enabled(true) // set false if email verification enabled
                .createdAt(Instant.now())
                .build();
        userRepository.save(user);
        // Optionally: send verification email
        return Map.of("message", "User registered successfully");
    }

    public AuthResponse login(com.example.auth.dto.LoginRequest req) {
        // authenticate using UserDetailsService or manual check
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) throw new RuntimeException("Invalid credentials");

        Map<String,Object> claims = Map.of("role", user.getRole().name());
        String access = jwtUtil.generateAccessToken(user.getEmail(), claims);
        String refresh = UUID.randomUUID().toString();
        RefreshToken rt = RefreshToken.builder().token(refresh).user(user).expiryDate(Instant.now().plusMillis(2592000000L)).build();
        refreshTokenRepository.save(rt);
        return AuthResponse.builder().accessToken(access).refreshToken(refresh).build();
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        RefreshToken rt = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (rt.getExpiryDate().isBefore(Instant.now())) throw new RuntimeException("Expired refresh token");
        User user = rt.getUser();
        Map<String,Object> claims = Map.of("role", user.getRole().name());
        String access = jwtUtil.generateAccessToken(user.getEmail(), claims);
        return AuthResponse.builder().accessToken(access).refreshToken(refreshToken).build();
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).ifPresent(rt -> refreshTokenRepository.delete(rt));
    }
}
