package com.crlm.exception;

import com.crlm.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private  ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message){
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message,Instant.now()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    };

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument( IllegalArgumentException ex){
        ErrorResponse error= new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),Instant.now());
        return  ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex){
        ErrorResponse error= new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",Instant.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExists(UserAlreadyExistsException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessRuleException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(400, ex.getMessage(), Instant.now())
        );
    }
}
