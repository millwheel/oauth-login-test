package com.example.loginback.security.jwt;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import static com.example.loginback.security.jwt.JwtCookieName.JWT_COOKIE_NAME;

@Component
public class JwtCookieProvider {

    public Cookie createLoginCookie(String token) {
        Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, token);
        jwtCookie.setHttpOnly(true); // Block the JS approach to Cookie
        jwtCookie.setMaxAge(2 * 60 * 60); // 2 Hours valid Cookie
        jwtCookie.setPath("/"); // Open for all path in domain
        return jwtCookie;
    }

    public Cookie createLogoutCookie() {
        Cookie jwtCookie = new Cookie(JWT_COOKIE_NAME, null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        return jwtCookie;
    }

}
