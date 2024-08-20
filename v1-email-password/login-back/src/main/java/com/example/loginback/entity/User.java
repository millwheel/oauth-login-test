package com.example.loginback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    private Long id;
    private String email;
    private String password;
    private String name;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {

    }
}
