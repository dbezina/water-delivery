package com.bezina.water_delivery.auth_service.exception_handler;

import org.springframework.http.HttpStatus;

public class InvalidJwtException extends BusinessException {

    public InvalidJwtException() {
        super(
                "INVALID_JWT",
                HttpStatus.UNAUTHORIZED,
                "JWT token is invalid or expired"
        );
    }
}