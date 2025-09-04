package com.bezina.water_delivery.gateway.config;

import com.bezina.water_delivery.gateway.Security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
 /*   @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500")); // только фронт
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
       // configuration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        configuration.setAllowCredentials(true); // если нужно передавать cookie/JWT
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
*/
 @Bean
 public CorsConfigurationSource corsConfigurationSource() {
     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     CorsConfiguration config = new CorsConfiguration();
     System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
     // Разрешить все методы (GET, POST, etc.)
     config.setAllowedMethods(List.of("*"));

     // Разрешить все заголовки
     config.setAllowedHeaders(List.of("*"));

     // Укажите один конкретный домен, для которого разрешены запросы
     // Замените "https://ваш-адрес.com" на реальный URL
     config.setAllowedOrigins(List.of("http://127.0.0.1:5500"));

     // Разрешить отправку учетных данных (если необходимо)
     config.setAllowCredentials(true);

     // Примените конфигурацию для всех путей приложения
     source.registerCorsConfiguration("/**", config);
     System.out.println("size "+List.of("http://127.0.0.1:5500").size());
     System.out.println(config.getAllowedOrigins().size());
     return source;
 }
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            //    .cors().configurationSource(corsConfigurationSource())
              .cors(cors -> cors.configurationSource(corsConfigurationSource()))

              //  .and()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/auth/**").permitAll() // логин/регистрация без токена
                .anyExchange().authenticated()       // все остальные требуют токен
                .and()
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
