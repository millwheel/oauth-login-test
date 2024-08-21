package com.example.loginback.service;

import com.example.loginback.entity.User;
import com.example.loginback.repository.UserRepository;
import com.example.loginback.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        try {
            if (!PasswordEncoder.verifyPassword(password, user.getHashedPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void createUser(String email, String password, String name){
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        String hashedPassword;
        try {
            hashedPassword = PasswordEncoder.encodePasswordBySHA256(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = new User(email, hashedPassword, name);
        userRepository.save(user);
    }

}
