package com.bezina.water_delivery.gateway.integration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtFilterIntegrationTest extends BaseGatewayTest{

    private String validToken() {
        return Jwts.builder()
                .setSubject("test-user")
                .claim("role", "USER")
                .signWith(Keys.hmacShaKeyFor(
                        Decoders.BASE64.decode("VGhpc0lzQVN1cGVyU2VjcmV0S2V5Rm9yVGVzdHMxMjM0NTY3ODkw")
                ))
                .compact();
    }
    @Test
    void requestWithoutTokenShouldReturn401() {
        webClient.get()
                .uri("/user/orders")
                .exchange()
                .expectStatus().isUnauthorized();
    }
    @Test
    void invalidTokenShouldReturn401() {

        webClient.get()
                .uri("/user/orders")
                .header("Authorization", "Bearer broken.token.here")
                .exchange()
                .expectStatus().isUnauthorized();
    }
    @Test
    void validTokenShouldPassToOrderService() {

        orderServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody("orders-ok")
        );

        webClient.get()
                .uri("/user/orders")
                .header("Authorization", "Bearer " + validToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("orders-ok");
    }
    @Test
    void shouldAddUserHeaders() throws InterruptedException {

        orderServer.enqueue(new MockResponse().setBody("ok"));

        webClient.get()
                .uri("/user/orders")
                .header("Authorization", "Bearer " + validToken())
                .exchange()
                .expectStatus().isOk();

        RecordedRequest request = orderServer.takeRequest();

        assertEquals("test-user", request.getHeader("X-User-Id"));
        assertEquals("ROLE_USER", request.getHeader("X-User-Role"));
    }
}
