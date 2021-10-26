package com.makul.fitness.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private String message;
    private Exception exception;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
