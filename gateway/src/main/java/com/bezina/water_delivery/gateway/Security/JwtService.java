package com.bezina.water_delivery.gateway.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JwtService {

    public String getSecret() {
        return secret;
    }

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token)
                .getBody();
        System.out.println("claims.get"+claims.get("role", String.class));
        return claims.get("role", String.class);
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
               // .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();

    }
}
