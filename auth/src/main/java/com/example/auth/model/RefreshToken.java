package com.example.auth.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String token;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    private Instant expiryDate;
}