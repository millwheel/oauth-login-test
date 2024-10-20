package com.example.loginback.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenManager {

    private final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(JwtSecret.SECRET_KEY).build();
    private static final long EXPIRATION_TIME = 5 * 60 * 1000;

    public String generateJwtToken(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
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

    public boolean validateJwtToken(String token) {
        try {
            Claims body = jwtParser
                    .parseClaimsJws(token)
                    .getBody();
            return !body.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public OAuth2AuthenticationToken getAuthenticationFromJwtToken(String token) {
        Claims body = jwtParser.parseClaimsJws(token).getBody();
        Map<String, Object> attributes = getAttributesFromClaims(body);
        List<GrantedAuthority> authorities = getAuthoritiesFromClaims(body);
        String registrationId = body.get("o2p", String.class);
        Instant issuedAt = Instant.ofEpochSecond(body.get("iat", Long.class));
        Instant expirationAt = Instant.ofEpochSecond(body.get("exp", Long.class));

        DefaultOidcUser principal = new DefaultOidcUser(authorities, new OidcIdToken(token, issuedAt, expirationAt, attributes), "sub");
        return new OAuth2AuthenticationToken(principal, authorities, registrationId);
    }

    private Map<String, Object> getAttributesFromClaims(Claims body) {
        String userId = body.getSubject();
        String email = body.get("email", String.class);
        Boolean emailVerified = body.get("email_verified", Boolean.class);
        String name = body.get("name", String.class);
        String givenName = body.get("given_name", String.class);
        String familyName = body.get("family_name", String.class);

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", userId);
        attributes.put("email", email);
        attributes.put("email_verified", emailVerified);
        attributes.put("name", name);
        attributes.put("given_name", givenName);
        attributes.put("family_name", familyName);
        return attributes;
    }

    private List<GrantedAuthority> getAuthoritiesFromClaims(Claims claims) {
        List<String> roles = claims.get("authorities", List.class);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
