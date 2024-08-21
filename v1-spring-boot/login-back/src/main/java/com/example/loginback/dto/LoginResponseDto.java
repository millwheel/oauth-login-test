package com.example.loginback.dto;

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
}
