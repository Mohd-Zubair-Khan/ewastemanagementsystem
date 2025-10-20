package com.example.auth.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileDto {
    private String name;
    private String phone;
    private String pickupAddress;
}