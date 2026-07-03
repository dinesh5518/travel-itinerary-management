package com.examly.travel_itinerary_management.controller;

import com.examly.travel_itinerary_management.model.User;
import com.examly.travel_itinerary_management.model.dto.req.RegisterRequest;
import com.examly.travel_itinerary_management.model.dto.res.AuthResponse;
import com.examly.travel_itinerary_management.service.AuthService;
import com.examly.travel_itinerary_management.model.dto.req.LoginRequest;
import com.examly.travel_itinerary_management.security.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try {
            User user = authService.register(request);

            return ResponseEntity.ok(
                    new AuthResponse(
                            null,
                            "Registration successful",
                            user.getRole().toString()
                    )
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
    
        try {
            String token = authHeader.replace("Bearer ", "");
            authService.logout(token);
            return ResponseEntity.ok("Logged out successfully");
        
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Logout failed");
        }
    }
}