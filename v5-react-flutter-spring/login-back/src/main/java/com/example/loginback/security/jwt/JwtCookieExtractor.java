package com.example.loginback.security.jwt;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import static com.example.loginback.security.jwt.constant.JwtCookieName.JWT_COOKIE_NAME;

@Component
public class JwtCookieExtractor {

    public String getTokenFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
