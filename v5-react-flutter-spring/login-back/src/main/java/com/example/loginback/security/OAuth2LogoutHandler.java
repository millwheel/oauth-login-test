package com.example.loginback.security;

import com.example.loginback.security.jwt.JwtCookieProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler implements LogoutHandler {

    private final JwtCookieProvider jwtCookieProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie jwtCookie = jwtCookieProvider.createLogoutCookie();
        response.addCookie(jwtCookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
