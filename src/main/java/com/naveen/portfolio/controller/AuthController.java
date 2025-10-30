package com.naveen.portfolio.controller;

import com.naveen.portfolio.exception.AuthException;
import com.naveen.portfolio.repository.UserRepository;
import com.naveen.portfolio.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository userRepo, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> payload) {
        var user = userRepo.findByUsername(payload.get("username"))
                .orElseThrow(() -> new AuthException("User not found"));

        if (!encoder.matches(payload.get("password"), user.getPassword()))
            throw new AuthException("Invalid credentials");

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return Map.of("token", token, "role", user.getRole());
    }

}
