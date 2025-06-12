package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.dto.AuthenticationRequest;
import com.ecommerce.backecommerce.dto.AuthenticationResponse;
import com.ecommerce.backecommerce.entity.Usuario;
import com.ecommerce.backecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Usuario user) {
        return ResponseEntity.ok(authService.register(user));
    }
    @GetMapping("/me")
    public ResponseEntity<Usuario> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Usuario usuario = authService.getUserFromToken(token); // Debes implementar esto en AuthenticationService
        return ResponseEntity.ok(usuario);
    }

}

