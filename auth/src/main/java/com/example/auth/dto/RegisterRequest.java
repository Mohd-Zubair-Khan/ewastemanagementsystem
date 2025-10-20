package com.example.auth.dto;


import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank
    private String name;


    @Email
    @NotBlank
    private String email;


    private String phone;


    @NotBlank
    @Size(min = 8, max = 128)
    private String password;
}