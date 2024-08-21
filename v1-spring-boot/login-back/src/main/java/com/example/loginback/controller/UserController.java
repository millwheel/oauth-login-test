package com.example.loginback.controller;


import com.example.loginback.dto.JoinDto;
import com.example.loginback.dto.LoginDto;
import com.example.loginback.entity.User;
import com.example.loginback.repository.UserRepository;
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

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request, @RequestBody LoginDto loginUser) {
        HttpSession session = request.getSession();

        if (session.getAttribute("userEmail") != null) {
            log.info(session.getAttribute("userEmail").toString());
            return new ResponseEntity<>("You are already logged in!", HttpStatus.CONFLICT);
        }
        Optional<User> user = userRepository.findByEmail(loginUser.getEmail());
        if (user.isEmpty()) {
            return new ResponseEntity<>("User is not present", HttpStatus.UNAUTHORIZED);
        }
        if (!user.get().getPassword().equals(loginUser.getPassword())) {
            return new ResponseEntity<>("Wrong password", HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute("userEmail", user.get().getEmail());
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
        if (userRepository.existsByEmail(newUser.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
        User user = new User(newUser);
        userRepository.save(user);
        return new ResponseEntity<>("Sign up Success", HttpStatus.CREATED);
    }
}
