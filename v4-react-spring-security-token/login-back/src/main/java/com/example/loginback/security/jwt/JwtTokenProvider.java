package com.example.loginback.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final long EXPIRATION_TIME = 5 * 60 * 1000;

    public String generateToken(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        String userId = oAuth2AuthenticationToken.getPrincipal().getName();
        String name = oAuth2AuthenticationToken.getPrincipal().getAttribute("name");

        return Jwts.builder()
                .setSubject(userId)
                .claim("name", name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(JwtSecret.SECRET_KEY)
                .compact();
    }

}
