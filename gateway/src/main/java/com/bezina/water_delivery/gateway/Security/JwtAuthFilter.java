package com.bezina.water_delivery.gateway.Security;

import com.bezina.water_delivery.gateway.config.GatewayConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Component
public class JwtAuthFilter implements WebFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayConfiguration.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        // пропускаем без проверки
        if (path.startsWith("/auth/")) {
            return chain.filter(exchange);
        }
        //extractJwt сам разбирается, откуда взять токен
        String token = extractJwt(exchange);
        if (token == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        try {
            Claims claims = jwtService.validateToken(token);
            Authentication auth = jwtService.getAuthentication(claims);

            String role = auth.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse(null);

            // формируем новые заголовки
            ServerWebExchange mutatedExchange = exchange
                            .mutate().request(exchange.getRequest()
                            .mutate().header("X-User-Id", claims.getSubject())
                            .header("X-User-Role", role).build()).build();

            return chain.filter(mutatedExchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));

        }// production style
        catch (JwtException | IllegalArgumentException e) {
            LOGGER.warn("JWT validation failed: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private String extractJwt(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // Временная поддержка SSE
        // TODO: Remove query-parameter JWT after migrating SSE to Authorization header.
        // Query parameters leak into browser history, logs and reverse proxies.
        if (exchange.getRequest().getPath().toString().startsWith("/notifications")) {
            return exchange.getRequest().getQueryParams().getFirst("token");
        }

        return null;
    }
}


