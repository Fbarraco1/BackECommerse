package com.ecommerce.backecommerce.entity;

import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + rol.name());
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
