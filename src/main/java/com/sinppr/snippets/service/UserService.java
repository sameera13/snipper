package com.sinppr.snippets.service;

import com.sinppr.snippets.model.User;
import com.sinppr.snippets.util.PasswordUtil;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;



@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    public User register(User user) {
        String hashed = PasswordUtil.hashPassword(user.getPassword());
        user.setPasswordHash(hashed);
        user.setId(idCounter.incrementAndGet());
        user.setPassword(null); // Do not store raw password
        users.put(user.getEmail(), user);
        return user;
    }

    public User authenticate(String email, String password) {
        User storedUser = users.get(email);
        if (storedUser != null && PasswordUtil.verifyPassword(password, storedUser.getPasswordHash())) {
            return new User(storedUser.getId(), storedUser.getEmail(), null, null); // Return sanitized user
        }
        return null;
    }
}
