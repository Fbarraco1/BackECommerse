package com.ecommerce.backecommerce.controller;

import com.ecommerce.backecommerce.dto.AuthenticationRequest;
import com.ecommerce.backecommerce.dto.AuthenticationResponse;
import com.ecommerce.backecommerce.entity.Usuario;
import com.ecommerce.backecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

