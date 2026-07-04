package com.bezina.water_delivery.auth_service.integration;

import com.bezina.water_delivery.auth_service.DTO.RegisterRequest;
import com.bezina.water_delivery.auth_service.entity.enums.Role;
import org.apache.kafka.image.ConfigurationDelta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AuthRegisterTest extends BaseIntegrationTest {


    @Test
    void registerShouldCreateUser() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test98");
        request.setPassword("9874");
        request.setRole(Role.USER);

        ResponseEntity<String> response =
                restTemplate.postForEntity("/auth/register", request, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void duplicateUserShouldReturnBadRequest() {
        RegisterRequest request1 = new RegisterRequest();
        request1.setUsername("test");
        request1.setPassword("1234");
        request1.setRole(Role.USER);

        RegisterRequest request2 = new RegisterRequest();
        request2.setUsername("test");
        request2.setPassword("1234");
        request2.setRole(Role.USER);

        restTemplate.postForEntity("/auth/register", request1, String.class);
        ResponseEntity<String> response =
                restTemplate.postForEntity("/auth/register", request2, String.class);

        assertEquals(400, response.getStatusCode().value());
    }
}