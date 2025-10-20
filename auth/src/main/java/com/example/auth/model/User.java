package com.example.auth.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Table(name = "users", indexes = {
        @Index(columnList = "email", name = "idx_user_email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false, unique = true)
    private String email;


    @Column(unique = true)
    private String phone;


    @Column(nullable = false)
    private String password; // hashed


    @Enumerated(EnumType.STRING)
    private Role role;


    private String profilePicUrl;


    private String pickupAddress;


    private Instant createdAt;


    private boolean enabled; // for email verification
}