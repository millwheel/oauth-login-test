package com.example.loginback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthCheckController {

    @GetMapping("/auth")
    public ResponseEntity<Map<String, Boolean>> checkAuth(Authentication authentication) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        response.put("isAuthenticated", isAuthenticated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
