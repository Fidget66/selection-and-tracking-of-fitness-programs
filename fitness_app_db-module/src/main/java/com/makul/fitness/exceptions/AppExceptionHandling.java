package com.makul.fitness.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandling extends ResponseEntityExceptionHandler {
    @ExceptionHandler(
            {NoEntityException.class,
            BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ApiError> handleCustomException(RuntimeException exception) {
        return new ResponseEntity<>(getApiError(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiError> handleUnprocessedException(Exception exception) {
        return new ResponseEntity<>(getApiError(exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(getApiError(ex), status);
    }

    private ApiError getApiError(Exception ex) {
        ApiError apiError = ApiError
                .builder()
                .exception(ex)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return apiError;
    }
}
