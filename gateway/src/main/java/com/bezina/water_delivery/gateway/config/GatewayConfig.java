package com.bezina.water_delivery.gateway.config;

import com.bezina.water_delivery.gateway.Security.JwtAuthFilter;
import com.bezina.water_delivery.gateway.Security.JwtAuthRouteFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class GatewayConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class);


 //   private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthRouteFilter jwtAuthRouteFilter;

    public GatewayConfig(JwtAuthFilter jwtAuthFilter, JwtAuthRouteFilter jwtAuthRouteFilter) {
        this.jwtAuthRouteFilter = jwtAuthRouteFilter;
     //   this.jwtAuthFilter = jwtAuthFilter;
        LOGGER.info("GatewayConfig created");

    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        LOGGER.info("RouteLocator");
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("http://localhost:8081"))
                .route("orders", r -> r.path("/user/**")
                        .filters(f -> f.filter(jwtAuthRouteFilter).filter((exchange, chain) -> {
                            String role = exchange.getAttribute("role");
                            LOGGER.info("role "+role);
                            if (!"ROLE_USER".equals(role)) {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            }
                            return chain.filter(exchange);
                        }))
                        .uri("http://localhost:8082"))
               /* .route("delivery-admin", r -> r.path("/admin/delivery/**")
                        .filters(f -> f.filter(jwtAuthRouteFilter).filter((exchange, chain) -> {
                            String role = exchange.getAttribute("role");
                            if (!"ROLE_ADMIN".equals(role)) {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            }
                            return chain.filter(exchange);
                        }))

                        .uri("http://localhost:8083"))*/
                .route("delivery-admin", r -> r.path("/admin/delivery/**")
                        .filters(f -> f.filter(jwtAuthRouteFilter)
                                .filter((exchange, chain) -> {
                                    String role = exchange.getAttribute("role");
                                    if (!"ROLE_ADMIN".equals(role)) {
                                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                        return exchange.getResponse().setComplete();
                                    }
                                    return chain.filter(exchange);
                                })
                                .rewritePath("/admin/(?<segment>.*)", "/admin/${segment}"))
                        .uri("http://localhost:8083"))
       /*         .route("delivery-admin", r -> r.path("/admin/delivery/{orderId}/status")
                        .filters(f -> f.filter(jwtAuthRouteFilter)
                                .filter((exchange, chain) -> {
                                    String role = exchange.getAttribute("role");
                                    if (!"ROLE_ADMIN".equals(role)) {
                                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                        return exchange.getResponse().setComplete();
                                    }
                                    return chain.filter(exchange);
                                })
                                .rewritePath("/admin/delivery/(?<orderId>.*)/status",
                                        "/admin/delivery/${orderId}/status")) // <-- тут переписываем путь
                        .uri("http://localhost:8083"))*/
                .route("delivery-courier", r -> r.path("/courier/**")
                        .filters(f -> f.filter(jwtAuthRouteFilter).filter((exchange, chain) -> {
                            String role = exchange.getAttribute("role");
                            if (!"ROLE_COURIER".equals(role)) {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            }
                            return chain.filter(exchange);
                        }))
                        .uri("http://localhost:8083"))

                .route("admin-inventory", r -> r.path("/admin/inventory/**")
                        .filters(f -> f.stripPrefix(1) // <-- удалит "/admin"
                                .filter(jwtAuthRouteFilter)
                                .filter((exchange, chain) -> {
                                    String role = exchange.getAttribute("role");
                                    if (!"ROLE_ADMIN".equals(role)) {
                                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                        return exchange.getResponse().setComplete();
                                    }
                                    return chain.filter(exchange);
                                }))
                        .uri("http://localhost:8084"))

                .route("admin-orders", r -> r.path("/admin/orders/**")
                        .filters(f -> f.filter(jwtAuthRouteFilter).filter((exchange, chain) -> {
                            String role = exchange.getAttribute("role");
                            if (!"ROLE_ADMIN".equals(role)) {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            }
                            return chain.filter(exchange);
                        }))
                        .uri("http://localhost:8082"))
                .build();
    }
}