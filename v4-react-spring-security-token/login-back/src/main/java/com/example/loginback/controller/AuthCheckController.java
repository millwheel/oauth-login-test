package com.example.loginback.controller;

import com.example.loginback.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthCheckController {


    private final JwtTokenProvider jwtTokenProvider;

    public AuthCheckController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/auth")
    public ResponseEntity<Map<String, Boolean>> checkAuth(@CookieValue(value = "JWT", required = false) String token) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isAuthenticated = token != null && jwtTokenProvider.validateToken(token);
        response.put("isAuthenticated", isAuthenticated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
