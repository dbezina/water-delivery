package com.bezina.water_delivery.auth_service.exception_handler;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException() {
        super(
                "INVALID_CREDENTIALS",
                HttpStatus.UNAUTHORIZED,
                "Invalid username or password"
        );
    }
}