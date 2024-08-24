package com.example.loginback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenInfoDto {
    private String accessToken;
    private String expiresIn;

}
