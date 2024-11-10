package com.example.loginback.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public record JwtSecret(Key secretKey) {

    public static final JwtSecret jwtSecret = new JwtSecret(Keys.secretKeyFor(SignatureAlgorithm.HS256));

    public JwtSecret(Key secretKey) {
        this.secretKey = secretKey;
    }

    public static Key getSecretKey() {
        return jwtSecret.secretKey;
    }
}