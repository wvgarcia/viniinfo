package com.umg.demo.jwt.demo_jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTUtil {
    // Clave secreta para firmar el token
    // Se recomienda una clave de al menos 256 bits para HS256
    private static final String SECRET = "clavesecretas$$";
    
    // Tiempo de expiración del token (ejemplo: 1 día en milisegundos)
    private static final long EXPIRATION_TIME = 86400000;
    
    // Prefijo que se espera en el header (por ejemplo, "Bearer ")
    private static final String PREFIX = "Bearer ";
    
    // Nombre del header donde se envía el token (por ejemplo, "Authorization")
    private static final String HEADER = "Authorization";
    // Método para generar un token JWT a partir del nombre de usuario
    
    public static String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // En JJWT 0.9.1 se utiliza: signWith(SignatureAlgorithm, byte[])
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    
    // Método para validar un token JWT y extraer sus "claims" a partir de la solicitud HTTP
    public static Claims validarToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (token != null && token.startsWith(PREFIX)) {
            // Remueve el prefijo para obtener el token limpio
            token = token.replace(PREFIX, "");
            return Jwts.parser()
                    .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        }
        return null;
    }
}