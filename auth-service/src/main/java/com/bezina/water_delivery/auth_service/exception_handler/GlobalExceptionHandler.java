package com.bezina.water_delivery.auth_service.exception_handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorResponse> handleDataIntegrity(
//            DataIntegrityViolationException ex) {
//
//        ErrorResponse response =
//                new ErrorResponse(
//                        Instant.now(),
//                        HttpStatus.CONFLICT.value(),
//                        "Duplicate resource",
//                        "Username already exists"
//                );
//
//        return ResponseEntity
//                .status(HttpStatus.CONFLICT)
//                .body(response);
//    }
//@ExceptionHandler(DuplicateUsernameException.class)
//public ResponseEntity<ErrorResponse> handleDuplicate(
//        DuplicateUsernameException ex) {
//
//    return ResponseEntity.status(HttpStatus.CONFLICT)
//            .body(new ErrorResponse(
//                    Instant.now(),
//                    HttpStatus.CONFLICT.value(),
//                    "USER_ALREADY_EXISTS",
//                    ex.getMessage()
//            ));
    //}
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex) {

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                ex.getStatus().value(),
                ex.getCode(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(response);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidation(
//            MethodArgumentNotValidException ex) {
//
//        String message = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .findFirst()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .orElse("Validation failed");
//
//        ErrorResponse response = new ErrorResponse(
//                Instant.now(),
//                HttpStatus.BAD_REQUEST.value(),
//                "VALIDATION_ERROR",
//                message
//        );
//
//        return ResponseEntity.badRequest().body(response);
//    }
}