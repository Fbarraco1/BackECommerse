package com.ecommerce.backecommerce.service;

import com.ecommerce.backecommerce.entity.Usuario;
import com.ecommerce.backecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true) // ← Agregar esta anotación
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 CustomUserDetailsService - Buscando usuario: " + username);
        System.out.println("📏 Length del username: " + username.length());

        try {
            // Debug: Ver todos los usuarios (TEMPORAL - remover en producción)
            List<Usuario> todosUsuarios = usuarioRepository.findAll();
            System.out.println("📊 Total usuarios en BD: " + todosUsuarios.size());
            todosUsuarios.forEach(u ->
                    System.out.println("  👤 Email: '" + u.getEmail() + "' (activo: " + u.isActivo() + ")")
            );

            // Buscar por email
            Usuario usuario = usuarioRepository.findByEmail(username)
                    .orElseThrow(() -> {
                        System.out.println("❌ Usuario no encontrado en repository: " + username);
                        return new UsernameNotFoundException("Usuario no encontrado: " + username);
                    });

            System.out.println("✅ Usuario encontrado: " + usuario.getEmail() + " - Rol: " + usuario.getRol() + " - Activo: " + usuario.isActivo());

            // Verificar si está activo
            if (!usuario.isActivo()) {
                System.out.println("⚠️ Usuario inactivo: " + usuario.getEmail());
                throw new UsernameNotFoundException("Usuario inactivo: " + username);
            }

            return usuario;
        } catch (Exception e) {
            System.out.println("💥 Error en loadUserByUsername: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}