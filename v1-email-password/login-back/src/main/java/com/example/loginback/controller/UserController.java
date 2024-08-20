package com.example.loginback.controller;


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
    public String login(@RequestBody User loginUser, HttpSession session) {
        Optional<User> user = userRepository.findById(loginUser.getId());
        if (user.isPresent() && user.get().getPassword().equals(loginUser.getPassword())) {
            session.setAttribute("userId", user.get().getId());
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
    public String join(@RequestBody User newUser) {
        if (userRepository.existsById(newUser.getId())) {
            return "User already exists";
        }
        userRepository.save(newUser);
        return "Sign up Success";
    }
}
