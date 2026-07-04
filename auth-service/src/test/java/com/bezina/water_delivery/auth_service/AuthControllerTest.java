package com.bezina.water_delivery.auth_service;

import com.bezina.water_delivery.auth_service.DTO.AuthResponse;
import com.bezina.water_delivery.auth_service.DTO.LoginRequest;
import com.bezina.water_delivery.auth_service.DTO.RegisterRequest;
import com.bezina.water_delivery.auth_service.entity.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void helloShouldReturnOk() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/hello", String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("hello successfully", response.getBody());
    }
    @Test
    void registerShouldCreateUser() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test");
        request.setPassword("1234");
        request.setRole(Role.USER);

        ResponseEntity<String> response =
                restTemplate.postForEntity("/register", request, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void loginShouldReturnToken() {
        // сначала регистрируем пользователя
        RegisterRequest register = new RegisterRequest();
        register.setUsername("test");
        register.setPassword("1234");
        register.setRole(Role.USER);

        restTemplate.postForEntity("/register", register, String.class);

        // логинимся
        LoginRequest login = new LoginRequest();
        login.setUsername("test");
        login.setPassword("1234");

        ResponseEntity<AuthResponse> response =
                restTemplate.postForEntity("/login", login, AuthResponse.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().getToken());
    }
}