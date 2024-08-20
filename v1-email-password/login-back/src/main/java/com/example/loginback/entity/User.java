package com.example.loginback.entity;

import com.example.loginback.dto.JoinDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String password;
    private String name;

    public User(JoinDto joinDto) {
        this.email = joinDto.getEmail();
        this.password = joinDto.getPassword();
        this.name = joinDto.getName();
    }

}
