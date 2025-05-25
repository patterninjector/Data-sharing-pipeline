package com.tub.ise.authenticationservice.service;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, String> users = new HashMap<>();

    public UserService() {
        // Example in-memory users
        users.put("sepide", "pass123");
        users.put("admin", "admin123");
    }

    public boolean validateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}

