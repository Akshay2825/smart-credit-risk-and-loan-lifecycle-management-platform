package com.crlm.exception;

import com.crlm.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Business rule violations (400).
     */
    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessRuleViolation(
            BusinessRuleViolationException ex
    ) {
        ErrorResponseDTO response =
                new ErrorResponseDTO(
                        ex.getMessage(),
                        "BUSINESS_RULE_VIOLATION"
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * Resource not found (404).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(
            ResourceNotFoundException ex
    ) {
        ErrorResponseDTO response =
                new ErrorResponseDTO(
                        ex.getMessage(),
                        "RESOURCE_NOT_FOUND"
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    /**
     * Fallback for unexpected errors (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpectedException(
            Exception ex
    ) {
        ErrorResponseDTO response =
                new ErrorResponseDTO(
                        "Something went wrong. Please contact support.",
                        "INTERNAL_SERVER_ERROR"
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
