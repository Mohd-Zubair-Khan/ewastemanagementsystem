package com.example.auth.controller;


import com.example.auth.dto.*;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;


    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> profile(Authentication authentication) {
        String email = authentication.getName();
        User u = userRepository.findByEmail(email).orElseThrow();
        UserProfileDto dto = UserProfileDto.fromEntity(u);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(Authentication authentication, @RequestBody UpdateProfileDto dto) {
        String email = authentication.getName();
        User u = userRepository.findByEmail(email).orElseThrow();
        u.setName(dto.getName());
        u.setPhone(dto.getPhone());
        u.setPickupAddress(dto.getPickupAddress());
        userRepository.save(u);
        return ResponseEntity.ok(UserProfileDto.fromEntity(u));
    }
}
