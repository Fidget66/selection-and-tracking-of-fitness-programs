package com.makul.fitness.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiError {
    private final String message;
    private final Exception exception;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
    private final String exceptionClassName;

    public ApiError(String message, Exception exception, HttpStatus status, LocalDateTime timestamp, String exceptionClassName) {
        this.message = message;
        this.exception = exception;
        this.status = status;
        this.timestamp = timestamp;
        this.exceptionClassName = exceptionClassName;
    }
}
