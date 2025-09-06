package com.bezina.water_delivery.gateway.config;

import com.bezina.water_delivery.gateway.Security.JwtAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class GatewayConfiguration {

    @Value("${services.auth-service}")
    private String authServiceUri;

    @Value("${services.order-service}")
    private String orderServiceUri;

    @Value("${services.delivery-service}")
    private String deliveryServiceUri;

    @Value("${services.inventory-service}")
    private String inventoryServiceUri;

    @Value("${services.notification-service}")
    private String notificationServiceUri;

        private static final Logger LOGGER = LoggerFactory.getLogger(GatewayConfiguration.class);
        private final JwtAuthFilter jwtAuthFilter;

    public GatewayConfiguration(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

//    @Bean
//        public RouteLocator routes(RouteLocatorBuilder builder) {
//            LOGGER.info("RouteLocator configured");
//
//            return builder.routes()
//                    .route("auth-service", r -> r.path("/auth/**")
//                            .uri("http://auth-service:8081"))
//
//                    .route("orders", r -> r.path("/user/**")
//                            .uri("http://order-service:8082"))
//                    .route("delivery-admin", r -> r.path("/admin/delivery/**")
//                            .uri("http://delivery-service:8083"))
//                    .route("delivery-courier", r -> r.path("/courier/**")
//                            .uri("http://delivery-service:8083"))
//                    .route("admin-inventory", r -> r.path("/admin/inventory/**")
//                            .filters(f -> f
//                                    .stripPrefix(1) // удаляет первый сегмент пути "/admin"
//                            )
//                            .uri("http://inventory-service:8084"))
//                /*    .route("payment", r -> r.path("/payment/**")
//                            .uri("http://payment-service:8085"))*/
//                    .route("notifications", r -> r.path("/notifications/**")
//                            .uri("http://notification-service:8086"))
//                    .build();
//        }
@Bean
public RouteLocator routes(RouteLocatorBuilder builder) {
    LOGGER.info("RouteLocator configured");

    return builder.routes()
            .route("auth-service", r -> r.path("/auth/**")
                    .uri(authServiceUri))
            .route("orders", r -> r.path("/user/**")
                    .uri(orderServiceUri))
            .route("delivery-admin", r -> r.path("/admin/delivery/**")
                    .uri(deliveryServiceUri))
            .route("delivery-courier", r -> r.path("/courier/**")
                    .uri(deliveryServiceUri))
            .route("admin-inventory", r -> r.path("/admin/inventory/**")
                    .filters(f -> f
                            .stripPrefix(1) // удаляет первый сегмент пути "/admin"
                    )
                    .uri(inventoryServiceUri))
            /*    .route("payment", r -> r.path("/payment/**")
                        .uri("http://payment-service:8085"))*/
            .route("notifications", r -> r.path("/notifications/**")
                    .uri(notificationServiceUri))
            .build();
}
        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            return http
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                    .authorizeExchange(exchanges -> exchanges
                            .pathMatchers("/auth/**").permitAll()
                            .anyExchange().authenticated()
                    )
                    .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                    .build();
        }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // Разрешить все методы (GET, POST, etc.)
        config.setAllowedMethods(List.of("*"));

        // Разрешить все заголовки
        config.setAllowedHeaders(List.of("*"));

        // Укажите один конкретный домен, для которого разрешены запросы
        // Замените "https://ваш-адрес.com" на реальный URL
        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:3000"));

        // Разрешить отправку учетных данных (если необходимо)
        config.setAllowCredentials(true);

        // Примените конфигурацию для всех путей приложения
        source.registerCorsConfiguration("/**", config);
        System.out.println("size "+List.of("http://127.0.0.1:5500", "http://localhost:3000").size());
        System.out.println(config.getAllowedOrigins().size());
        return source;
    }
}
