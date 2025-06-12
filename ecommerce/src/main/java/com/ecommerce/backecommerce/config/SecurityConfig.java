package com.ecommerce.backecommerce.config;

import com.ecommerce.backecommerce.security.JwtAuthenticationFilter;
import com.ecommerce.backecommerce.service.CustomUserDetailsService; // ← Importar tu servicio
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService; // ← Cambiar aquí

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // === RUTAS PÚBLICAS (visitante) ===
                        .requestMatchers(
                                "api/auth/**",                   // login, registro
                                "/productos/**",              // navegación general
                                "/categorias/**",
                                "/api/imagenes/**",
                                "/talles/**",
                                "/producto/**",
                                "/cantidad/**",
                                "/tipos/**",
                                "/talleproductos/**"
                        ).permitAll()

                        // === RUTAS SÓLO PARA USUARIOS LOGUEADOS (USER) ===
                        .requestMatchers(
                                "/direccion/**",
                                "/ordendecompra/**",
                                "/detalleorden/**"
                        ).hasRole("USER")

                        // === CONTROL POR MÉTODO PARA BLOQUEAR CAMBIOS A VISITANTES O USERS ===
                        .requestMatchers(HttpMethod.POST, "/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/direccion/**").hasAnyRole("ADMIN","USER")
                        .requestMatchers(HttpMethod.PUT, "/direccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/direccion/usuario/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/direccion/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/talles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/talles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/talles/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/imagenes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/imagenes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/imagenes/**").hasRole("ADMIN")

                        // === RUTAS DE ADMINISTRADOR ===
                        .requestMatchers(
                                "/productos/admin/productos/**",
                                "/api/admin/**",
                                "/categorias/**"
                        ).hasRole("ADMIN")

                        // === TODO LO DEMÁS REQUIERE AUTENTICACIÓN ===
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService); // ← Usar tu servicio
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}