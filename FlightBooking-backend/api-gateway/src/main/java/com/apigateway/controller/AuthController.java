package com.apigateway.controller;

import com.apigateway.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.secret}")
    private String secret;

    // Hardcoded user roles for now — replace with DB later
    private final Map<String, String> userRoles = Map.of(
            "admin", "ADMIN",
            "customer", "CUSTOMER",
            "buyer", "CUSTOMER"
    );

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {

        String username = data.get("username");
        String password = data.get("password");

        // Dummy password check — replace with real DB later
        if (username == null || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        // assign role
        String role = userRoles.getOrDefault(username.toLowerCase(), "CUSTOMER");

        // generate token
        String token = TokenGenerator.generate(
                secret,
                username,
                List.of(role),
                3600 * 5 // 5 hours
        );

        return ResponseEntity.ok(
                Map.of(
                        "username", username,
                        "role", role,
                        "token", token
                )
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> data) {
        return ResponseEntity.ok("Signup success");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout success");
    }
}
