package com.bezina.water_delivery.gateway.integration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CorsRawCurlLikeTest extends BaseGatewayTest {

    @LocalServerPort
    private int port;

    @Test
    void rawOptionsRequestLikeCurl() throws Exception {
        String origin = "http://localhost:3000";

        // печатаем коды символов строки origin - чтобы исключить скрытые символы
        System.out.println("Origin char codes:");
        origin.chars().forEach(c -> System.out.print(c + " "));
        System.out.println();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/auth/login"))
                .method("OPTIONS", HttpRequest.BodyPublishers.noBody())
                .header("Origin", origin)
                .header("Access-Control-Request-Method", "POST")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Headers:");
        response.headers().map().forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("Body: [" + response.body() + "]");
    }
}