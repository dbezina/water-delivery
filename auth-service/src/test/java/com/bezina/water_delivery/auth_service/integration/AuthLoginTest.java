package com.bezina.water_delivery.auth_service.integration;

import com.bezina.water_delivery.auth_service.DTO.AuthResponse;
import com.bezina.water_delivery.auth_service.DTO.LoginRequest;
import com.bezina.water_delivery.auth_service.DTO.RegisterRequest;
import com.bezina.water_delivery.auth_service.entity.enums.Role;
import com.bezina.water_delivery.auth_service.exception_handler.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthLoginTest extends BaseIntegrationTest {

    @Test
    void loginShouldReturnJwtToken() {
        RegisterRequest register = new RegisterRequest();
        register.setUsername("test");
        register.setPassword("1234");
        register.setRole(Role.USER);

        restTemplate.postForEntity("/auth/register", register, String.class);

        LoginRequest login = new LoginRequest();
        login.setUsername("test");
        login.setPassword("1234");

       ResponseEntity<AuthResponse> response =
               restTemplate.postForEntity("/auth/login", login, AuthResponse.class);


        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().getToken());
        assertEquals("test", response.getBody().getUserName());

    }

    @Test
    void loginWithUnknownUserShouldReturn401() {

        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("1234");

        ResponseEntity<ErrorResponse> response =
                restTemplate.postForEntity("/auth/login", request, ErrorResponse.class);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("INVALID_CREDENTIALS", response.getBody().error());
    }
    @Test
    void loginWithWrongPasswordShouldReturn401() {

        RegisterRequest register = new RegisterRequest();
        register.setUsername("wrong");
        register.setPassword("1234");
        register.setRole(Role.USER);

        restTemplate.postForEntity("/auth/register", register, String.class);

        LoginRequest login = new LoginRequest();
        login.setUsername("wrong");
        login.setPassword("wrong");

        ResponseEntity<ErrorResponse> response =
                restTemplate.postForEntity("/auth/login", login, ErrorResponse.class);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("INVALID_CREDENTIALS", response.getBody().error());
    }
}