package com.example.loginback.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck(){
        return "Server is running successfully";
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Boolean>> checkSession(HttpSession session) {
        Map<String, Boolean> response = new HashMap<>();
        if (session.getAttribute("userEmail") != null) {
            response.put("isAuthenticated", true);
        } else {
            response.put("isAuthenticated", false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
