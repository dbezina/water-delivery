package com.bezina.water_delivery.gateway.integration;

import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CorsIntegrationTest extends BaseGatewayTest {

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void shouldAllowConfiguredOrigin() {
        webTestClient.options()
                .uri("/auth/login")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "POST")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .valueEquals("Access-Control-Allow-Origin", "http://localhost:3000");
    }

    @Test
    void shouldRejectUnknownOrigin() {

        webTestClient.options()
                .uri("/auth/login")
                .header("Origin", "http://evil.com")
                .header("Access-Control-Request-Method", "POST")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldExposeAllowedMethods() {

        webTestClient.options()
                .uri("/auth/login")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "POST")
                .exchange()
                .expectHeader()
                .valueMatches("Access-Control-Allow-Methods",
                        ".*POST.*")
        ;
    }

    @Test
    void shouldAllowAuthorizationHeaderInPreflight() {
        webTestClient.options()
                .uri("/auth/login")
                .header("Origin", "http://localhost:3000")
                .header("Access-Control-Request-Method", "POST")
                .header("Access-Control-Request-Headers", "Authorization")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .value("Access-Control-Allow-Headers",
                        headerValue -> assertThat(headerValue).contains("Authorization"));
    }

    @Test
    void shouldExposeAuthorizationHeaderInActualResponse() throws Exception {
        // мок ответа от auth-сервиса, чтобы реальный (не preflight) запрос дошёл до конца
        authServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Authorization", "Bearer some-token")
                .setBody("{}"));

        webTestClient.post()
                .uri("/auth/login")
                .header("Origin", "http://localhost:3000")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .value("Access-Control-Expose-Headers",
                        headerValue -> assertThat(headerValue).contains("Authorization"));
    }
}