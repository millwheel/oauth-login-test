package com.example.loginbackv2.dto;

import lombok.Data;

@Data
public class JoinRequestDto {
    private String email;
    private String password;
    private String name;
}
