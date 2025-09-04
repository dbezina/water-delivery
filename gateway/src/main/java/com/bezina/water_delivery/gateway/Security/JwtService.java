package com.bezina.water_delivery.gateway.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;

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
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException("Other Exception");
        }

    }


    public Authentication getAuthentication(Claims claims) {
        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        // нормализуем, чтобы Spring Security понял
        String grantedRole = role != null && role.startsWith("ROLE_") ? role : "ROLE_" + role;

        // Создаем Authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
        return auth;
    }
}
