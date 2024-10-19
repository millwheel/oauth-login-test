package com.example.loginback.security;

import com.example.loginback.security.jwt.JwtTokenManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenManager jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String token = jwtTokenProvider.generateToken(oauthToken);
        Cookie jwtCookie = createJwtCookie(token);
        response.addCookie(jwtCookie);
        response.sendRedirect("http://localhost:3000");
    }

    private Cookie createJwtCookie(String token) {
        Cookie jwtCookie = new Cookie("JWT", token);
        jwtCookie.setHttpOnly(true); // Block the JS approach to Cookie
        jwtCookie.setMaxAge(2 * 60 * 60); // 2 Hours valid Cookie
        jwtCookie.setPath("/"); // Open for all path in domain
        return jwtCookie;
    }


}
