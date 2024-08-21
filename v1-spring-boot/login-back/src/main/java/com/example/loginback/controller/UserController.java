package com.example.loginback.controller;


import com.example.loginback.dto.JoinDto;
import com.example.loginback.dto.LoginDto;
import com.example.loginback.entity.User;
import com.example.loginback.repository.UserRepository;
import com.example.loginback.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request, @RequestBody LoginDto loginUser) {
        HttpSession session = request.getSession();

        if (session.getAttribute("userEmail") != null) {
            log.info(session.getAttribute("userEmail").toString());
            return new ResponseEntity<>("You are already logged in!", HttpStatus.CONFLICT);
        }
        User user = userService.getUser(loginUser.getEmail(), loginUser.getPassword());
        session.setAttribute("userEmail", user.getEmail());
        return new ResponseEntity<>("Login Success", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        try {
            session.invalidate();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return new ResponseEntity<>("Logout Success", HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDto newUser, HttpSession session) {
        if (session.getAttribute("userEmail") != null) {
            log.info(session.getAttribute("userEmail").toString());
            return new ResponseEntity<>("You are logged in user. logout first.", HttpStatus.CONFLICT);
        }
        userService.createUser(newUser.getEmail(), newUser.getPassword(), newUser.getName());
        return new ResponseEntity<>("Sign up Success", HttpStatus.CREATED);
    }
}
