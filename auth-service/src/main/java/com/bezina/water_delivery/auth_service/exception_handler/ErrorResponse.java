package com.bezina.water_delivery.auth_service.exception_handler;

import java.time.Instant;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message
) {
}
