package com.ecommerce.backecommerce.config;

import com.ecommerce.backecommerce.security.JwtAuthenticationFilter;
import com.ecommerce.backecommerce.service.CustomUserDetailsService;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})  // habilita CORS sin usar .and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "api/auth/**",
                                "/productos/**",
                                "/categorias/**",
                                "/api/imagenes/**",
                                "/talles/**",
                                "/producto/**",
                                "/cantidad/**",
                                "/tipos/**",
                                "/talleproductos/**"
                        ).permitAll()
                        .requestMatchers(
                                "/direccion/**",
                                "/ordendecompra/**",
                                "/detalleorden/**"
                        ).hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/direccion/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/direccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/direccion/usuario/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/direccion/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/talles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/talles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/talles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/imagenes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/imagenes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/imagenes/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/productos/admin/productos/**",
                                "/api/admin/**",
                                "/categorias/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
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

    // --- Aquí está el bean CORS que faltaba, necesario para que http.cors() funcione bien ---
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Cambia si usas otro origen
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Si usas cookies o credenciales
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
