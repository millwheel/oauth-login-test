package com.example.loginback.security.jwt;

import com.example.loginback.security.jwt.constant.JwtSecret;
import com.example.loginback.security.model.ProviderUser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

    private static final long EXPIRATION_TIME = 5 * 60 * 1000;

    public String generateToken(ProviderUser providerUser, String registrationId) {
        String sub = providerUser.getSub();
        String email = providerUser.getEmail();
        String name = providerUser.getName();

        return Jwts.builder()
                .setSubject(sub)
                .claim("email", email)
                .claim("name", name)
                .claim("o2p", registrationId)
                .claim("authorities", providerUser.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(JwtSecret.SECRET_KEY)
                .compact();
    }

}
