package com.example.loginback.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    String email;
    String name;

    public UserInfoDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
