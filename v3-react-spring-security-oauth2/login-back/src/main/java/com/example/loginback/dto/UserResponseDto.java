package com.example.loginback.dto;

import com.example.loginback.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String provider;
    private String email;
    private String name;
    private List<String> authorities;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.provider = user.getProvider();
        this.email = user.getEmail();
        this.name = user.getName();
        this.authorities = user.getAuthorities();
    }
}
