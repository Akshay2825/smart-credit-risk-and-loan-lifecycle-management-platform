package com.crlm.dto;

import java.time.Instant;

public class ErrorResponseDTO {

    private final String message;
    private final String errorCode;
    private final Instant timestamp;

    public ErrorResponseDTO(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
