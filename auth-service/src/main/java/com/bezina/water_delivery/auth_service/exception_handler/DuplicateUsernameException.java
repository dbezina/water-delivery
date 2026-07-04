package com.bezina.water_delivery.auth_service.exception_handler;

import org.springframework.http.HttpStatus;

public class DuplicateUsernameException extends BusinessException {

    public DuplicateUsernameException(String username) {
        super(
                "USER_ALREADY_EXISTS",
                HttpStatus.CONFLICT,
                "Username '" + username + "' already exists"
        );
    }
}