package com.example.loginback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null){
            return "authenticated";
        }
        return "unauthenticated";
    }

    @GetMapping("/auth")
    public ResponseEntity<Map<String, Boolean>> checkAuth(Authentication authentication) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        response.put("isAuthenticated", isAuthenticated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
