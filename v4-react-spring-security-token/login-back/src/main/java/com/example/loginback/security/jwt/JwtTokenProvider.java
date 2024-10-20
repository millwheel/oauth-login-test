package com.example.loginback.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final long EXPIRATION_TIME = 5 * 60 * 1000;

    public String generateToken(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        String userId = oAuth2AuthenticationToken.getPrincipal().getName();
        Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
        String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        List<String> authorities = oAuth2AuthenticationToken.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String iss = attributes.get("iss").toString();
        String email = (String) attributes.get("email");
        Boolean emailVerified = (Boolean) attributes.get("email_verified");
        String name = (String) attributes.get("name");
        String givenName = (String) attributes.get("given_name");
        String familyName = (String) attributes.get("family_name");

        return Jwts.builder()
                .setSubject(userId)
                .claim("iss", iss)
                .claim("email", email)
                .claim("email_verified", emailVerified)
                .claim("name", name)
                .claim("given_name", givenName)
                .claim("family_name", familyName)
                .claim("o2p", registrationId)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(JwtSecret.SECRET_KEY)
                .compact();
    }

}
