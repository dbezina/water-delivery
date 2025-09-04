package com.bezina.water_delivery.gateway.Security;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
@Component
public class JwtAuthFilter implements WebFilter {
     private final JwtService jwtService;

     public JwtAuthFilter(JwtService jwtService) {
         this.jwtService = jwtService;
     }

     @Override
     public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
         String path = exchange.getRequest().getPath().toString();
         if (path.startsWith("/auth/")) {
             return chain.filter(exchange); // пропускаем без проверки
         }

         String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
             exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
             return exchange.getResponse().setComplete();
         }

         String token = authHeader.substring(7);
         exchange.getAttributes().put("role", jwtService.extractRole(token));

         try {
             Claims claims = jwtService.validateToken(token);
             String username = claims.getSubject();
             String role = claims.get("role", String.class);
          //   System.out.println("After Token Validation "+ claims.getSubject()+ " "+role);

             // Создаем Authentication
             List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
             Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

             // Пробрасываем userId и роль дальше в заголовках
             ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                     .header("X-User-Id", username)
                     .header("X-User-Role", role)
                     .build();

             ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
             return chain.filter(mutatedExchange)
                     .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));

         } catch (Exception e) {
             exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
             return exchange.getResponse().setComplete();
         }
     }

 }


