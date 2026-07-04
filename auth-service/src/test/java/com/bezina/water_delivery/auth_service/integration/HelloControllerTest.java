package com.bezina.water_delivery.auth_service.integration;


import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.testcontainers.DockerClientFactory;
import com.github.dockerjava.api.DockerClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloControllerTest extends BaseIntegrationTest {


    @Test
    void helloShouldReturnOk() {
        System.out.println("URL = /auth/hello");
        ResponseEntity<String> response =
                restTemplate.getForEntity("/auth/hello", String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("hello successfully", response.getBody());
    }
    /*
    // check if testcontainers can achieve docker engine
    @Test
    void debugDocker() {
        System.out.println("DOCKER_HOST = " + System.getenv("DOCKER_HOST"));
    }

    @Test
    void testDockerAccess() {
        DockerClient client = DockerClientFactory.instance().client();
        System.out.println(client);
    }*/
}