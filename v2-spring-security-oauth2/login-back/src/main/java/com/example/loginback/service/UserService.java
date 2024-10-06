package com.example.loginback.service;

import com.example.loginback.entity.User;
import com.example.loginback.security.model.ProviderUser;
import com.example.loginback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUser(String username){
        return userRepository.findByUsername(username);
    }

    public void AddUser(ProviderUser providerUser) {
        User user = new User(providerUser);
        userRepository.save(user);
    }
}
