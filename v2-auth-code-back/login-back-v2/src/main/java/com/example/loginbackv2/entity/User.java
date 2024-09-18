package com.example.loginbackv2.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String hashedPassword;
    private String name;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    public User(String email, String hashedPassword, String name) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.name = name;
    }

    public User(String email, String name, AuthProvider provider) {
        this.email = email;
        this.name = name;
        this.provider = provider;
    }
}
