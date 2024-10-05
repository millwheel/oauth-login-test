package com.example.loginback.repository;

import com.example.loginback.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final Map<String, Object> users = new HashMap<>();

    public User findByUsername(String username) {
        if (users.containsKey(username)) {
            return (User) users.get(username);
        }
        return null;
    }

    public void register(User user) {
        if (users.containsKey(user.getUsername())) {
            return;
        }
        users.put(user.getUsername(), user);
    }
}
