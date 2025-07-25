package com.sinppr.snippets.controller;

import com.sinppr.snippets.model.User;
import com.sinppr.snippets.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = service.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @GetMapping
    public ResponseEntity<?> login(@RequestParam(required = false) String email,
                                   @RequestParam(required = false) String password) {
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Missing email or password query parameter");
        }
        User user = service.authenticate(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        return ResponseEntity.ok(user);
    }
}
