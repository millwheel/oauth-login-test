package com.example.loginback.dto;

import lombok.Data;

@Data

public class TokenInfoDto {
    private String accessToken;
    private String idToken;
    private String expiresIn;

    public TokenInfoDto(String accessToken, String idToken, String expiresIn) {
        this.expiresIn = expiresIn;
        this.idToken = idToken;
        this.accessToken = accessToken;
    }

    public TokenInfoDto(String accessToken, String expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
