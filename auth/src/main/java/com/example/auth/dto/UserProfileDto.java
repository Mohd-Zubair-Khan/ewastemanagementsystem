package com.example.auth.dto;


import com.example.auth.model.User;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String pickupAddress;
    private String profilePicUrl;


    public static UserProfileDto fromEntity(User u) {
        return UserProfileDto.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .phone(u.getPhone())
                .pickupAddress(u.getPickupAddress())
                .profilePicUrl(u.getProfilePicUrl())
                .build();
    }
}