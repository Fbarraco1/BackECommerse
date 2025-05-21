package com.ecommerce.backecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends Base implements UserDetails {

    private String nombre;
    private String email;
    private String contrasenia;
    private String rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Simple rol-based authority
        return List.of(() -> rol);
    }

    @Override
    public String getPassword() {
        return contrasenia;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // o lógica propia
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // o lógica propia
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // o lógica propia
    }

    @Override
    public boolean isEnabled() {
        return true; // o lógica propia
    }
}
