package com.umg.demo.jwt.demo_jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends OncePerRequestFilter { 

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        try {
            if (existeJWTToken(request, response)) {
                Claims claims = JWTUtil.validarToken(request);
                // Validamos que exista el claim "sub" (subject)
                if (claims.getSubject() != null) {
                    setUpSpringAuthentication(claims.getSubject());
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    /**
     * Configura la autenticación en Spring Security a partir del nombre de usuario
     * (subject).
     * En este ejemplo, no se asignan authorities.
     */
    private void setUpSpringAuthentication(String username) {
        // Se crea un objeto de autenticación sin authorities (lista vacía)
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /**
     * Método auxiliar para verificar si existe el token JWT en la solicitud.
     * Por ejemplo, puede validar si el header "Authorization" existe y comienza con
     * "Bearer ".
     */
    private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader("Authorization");
        return authenticationHeader != null && authenticationHeader.startsWith("Bearer ");
    }


}