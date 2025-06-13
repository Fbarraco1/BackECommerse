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

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> {})  // Habilita CORS
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
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

                        // Rutas para usuarios con rol USER
                        .requestMatchers(
                                "/direccion/**",
                                "/ordendecompra/**",
                                "/detalleorden/**",
                                "/api/pagos/crear-preferencia"
                        ).hasRole("USER")

                        // Control por método y rol
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

                        // Rutas de administrador
                        .requestMatchers(
                                "/productos/admin/productos/**",
                                "/api/admin/**",
                                "/categorias/**"
                        ).hasRole("ADMIN")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Configuración de CORS para Spring Security
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Tu frontend
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false); // Cambia a true si usás cookies
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
}
