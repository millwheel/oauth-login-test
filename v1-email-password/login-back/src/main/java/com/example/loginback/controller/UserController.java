package com.example.loginback.controller;


import com.example.loginback.dto.JoinDto;
import com.example.loginback.dto.LoginDto;
import com.example.loginback.entity.User;
import com.example.loginback.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginUser, HttpSession session) {
        Optional<User> user = userRepository.findByEmail(loginUser.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(loginUser.getPassword())) {
            session.setAttribute("userEmail", user.get().getEmail());
            return "Login Success";
        } else {
            return "Login Failed";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logout Success";
    }

    @PostMapping("/join")
    public String join(@RequestBody JoinDto newUser) {
        if (userRepository.existsByEmail(newUser.getEmail())) {
            return "User already exists";
        }
        User user = new User(newUser);
        userRepository.save(user);
        return "Sign up Success";
    }
}
