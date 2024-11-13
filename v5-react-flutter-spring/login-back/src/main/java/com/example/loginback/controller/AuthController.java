package com.example.loginback.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkAuth(Authentication authentication) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isAuthenticated = authentication != null;
        response.put("isAuthenticated", isAuthenticated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/login/{provider}")
    public ResponseEntity<String> getAuthorizationUrl(@PathVariable String provider, HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = authorizationRequestResolver.resolve(request, provider);
        if (authorizationRequest != null) {
            String authorizationUrl = authorizationRequest.getAuthorizationUri();
            return ResponseEntity.ok(authorizationUrl);
        }
        return ResponseEntity.badRequest().body("Invalid provider or request");
    }

}
