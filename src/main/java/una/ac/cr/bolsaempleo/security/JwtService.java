package una.ac.cr.bolsaempleo.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generarToken(String username, String rol, Long id) {
        return Jwts.builder()
                .subject(username)
                .claim("rol", rol)
                .claim("id", id)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public Claims parsearToken(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String obtenerUsername(String token) {
        return parsearToken(token).getSubject();
    }

    public String obtenerRol(String token) {
        return parsearToken(token).get("rol", String.class);
    }

    public Long obtenerIdUsuario(String token) {
        return parsearToken(token).get("id", Long.class);
    }

    public boolean esValido(String token) {
        try {
            return parsearToken(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}