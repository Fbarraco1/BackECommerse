package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.dto.AuthenticationRequest;
import com.ecommerce.backecommerce.dto.AuthenticationResponse;
import com.ecommerce.backecommerce.entity.Rol;
import com.ecommerce.backecommerce.entity.Usuario;
import com.ecommerce.backecommerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasenia())
        );

        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse register(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalStateException("El email ya está registrado");
        }

        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.USER);
        }
        Usuario savedUser = usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(savedUser);
        return new AuthenticationResponse(jwtToken);
    }
    public Usuario getUserFromToken(String token) {
        String email = jwtService.extractUsername(token); // extrae el email del token
        return usuarioRepository.findByEmail(email).orElseThrow(); // o un DTO si preferís
    }


}
