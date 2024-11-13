package com.example.loginback.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> checkAuth(Authentication authentication) {
        Map<String, Boolean> authCheckMap = new HashMap<>();
        boolean isAuthenticated = authentication != null;
        authCheckMap.put("isAuthenticated", isAuthenticated);
        return authCheckMap;
    }


}
