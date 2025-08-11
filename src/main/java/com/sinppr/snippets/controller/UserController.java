package com.sinppr.snippets.controller;

import com.sinppr.snippets.model.User;
import com.sinppr.snippets.service.UserService;
import com.sinppr.snippets.util.JwtUtil;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;

    public UserController(UserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginViaHeader(@RequestHeader(name = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.startsWith("Basic ")) {
            return ResponseEntity.badRequest()
                    .body("Missing or invalid Authorization header. Use: 'Authorization: Basic base64(email:password)'");
        }

        try {
            String base64Credentials = authorization.substring("Basic ".length());
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            String[] parts = credentials.split(":", 2);

            if (parts.length != 2) {
                return ResponseEntity.badRequest().body("Invalid Basic auth format.");
            }

            String email = parts[0];
            String password = parts[1];

            User user = service.authenticate(email, password);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            String token = jwtUtil.generateToken(email); // ✅ instance method

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(Map.of("token", token));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Failed to decode Basic auth credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + e.getMessage());
        }
    }
}
