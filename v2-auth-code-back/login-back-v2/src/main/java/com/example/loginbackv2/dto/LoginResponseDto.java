package com.example.loginbackv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String message;
    private String accessToken;
    private Integer expiresIn;
    private String email;
    private String name;

    public LoginResponseDto(String message) {
        this.message = message;
    }
}
