package com.ecommerce.backecommerce.security;

import com.ecommerce.backecommerce.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        System.out.println("=== JWT Filter Debug ===");
        System.out.println("Auth Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        System.out.println("JWT Token: " + jwt.substring(0, Math.min(jwt.length(), 50)) + "...");

        try {
            username = jwtService.extractUsername(jwt);
            System.out.println("Extracted username: " + username);
        } catch (Exception e) {
            System.out.println("Error extracting username: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("User found: " + userDetails.getUsername());

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("Token is valid, setting authentication");
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("Token is NOT valid");
                }
            } catch (Exception e) {
                System.out.println("Error loading user: " + e.getMessage());
            }
        } else {
            System.out.println("Username is null or authentication already exists");
        }

        filterChain.doFilter(request, response);
    }
}

