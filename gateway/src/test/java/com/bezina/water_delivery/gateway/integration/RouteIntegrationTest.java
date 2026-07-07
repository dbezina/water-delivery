package com.bezina.water_delivery.gateway.integration;

import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

class RouteIntegrationTest extends BaseGatewayTest {

    @Test
    void shouldForwardRequestToAuthService() throws Exception {

        authServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody("hello successfully")
        );

        webClient.get()
                .uri("/auth/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("hello successfully");

    }

}