package com.hazlofacil.gestionproductos.controller;

import com.hazlofacil.gestionproductos.security.JwtUtil;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
   
        if (request.username.equals("admin") && request.password.equals("admin")) {
            String token = jwtUtil.generateToken(request.username);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).build();
    }

    public record AuthRequest(@NotBlank String username, @NotBlank String password) {}
}
