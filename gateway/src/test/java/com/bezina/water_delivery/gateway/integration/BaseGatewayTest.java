package com.bezina.water_delivery.gateway.integration;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public abstract class BaseGatewayTest {

    static MockWebServer authServer;
    static MockWebServer orderServer;

    @BeforeAll
    static void beforeAll() throws Exception {
        authServer = new MockWebServer();
        orderServer = new MockWebServer();

        authServer.start();
        orderServer.start();
    }

    @AfterAll
    static void afterAll() throws Exception {
        authServer.shutdown();
        orderServer.shutdown();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {

        registry.add(
                "services.auth-service",
                () -> authServer.url("/").toString()
        );
        registry.add(
                "services.order-service",
                () -> orderServer.url("/").toString()
        );
    }

    @Autowired
    protected WebTestClient webClient;

}