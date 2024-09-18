package com.example.loginbackv2.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "Welcome";
        } else {
            return "Please Login";
        }
    }

}
