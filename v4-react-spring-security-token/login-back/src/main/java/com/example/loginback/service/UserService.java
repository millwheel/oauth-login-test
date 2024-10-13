package com.example.loginback.service;

import com.example.loginback.entity.User;
import com.example.loginback.repository.UserRepository;
import com.example.loginback.security.model.ProviderUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String pid){
        return userRepository.findByPid(pid).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void AddUser(ProviderUser providerUser) {
        User user = new User(providerUser);
        userRepository.save(user);
    }

    public boolean isExist(String pid) {
        return userRepository.findByPid(pid).isPresent();
    }
}
