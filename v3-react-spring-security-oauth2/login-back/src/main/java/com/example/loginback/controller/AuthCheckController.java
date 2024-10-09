package com.example.loginback.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthCheckController {

    @GetMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Boolean>> checkAuth(Authentication authentication) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isAuthenticated = authentication != null;
        response.put("isAuthenticated", isAuthenticated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
