package com.makul.fitness.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoEntityException.class)
    private ResponseEntity<ApiError> handleNoEntityException(NoEntityException exception) {
        return new ResponseEntity<>(getApiError(exception,HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActiveProgramIsPresentException.class)
    private ResponseEntity<ApiError> handleActiveProgramIsPresentException(ActiveProgramIsPresentException exception) {
        System.out.println(exception.toString());
        return new ResponseEntity<>(getApiError(exception,HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewIsPresentException.class)
    private ResponseEntity<ApiError> handleReviewIsPresentException(ReviewIsPresentException exception) {
        return new ResponseEntity<>(getApiError(exception,HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScheduleIsPresentException.class)
    private ResponseEntity<ApiError> handleScheduleIsPresentException(ScheduleIsPresentException exception) {
        return new ResponseEntity<>(getApiError(exception,HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookmarkIsPresentException.class)
    private ResponseEntity<ApiError> handleBookmarkIsPresentException(BookmarkIsPresentException exception) {
        return new ResponseEntity<>(getApiError(exception,HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(getApiError(ex,status),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiError getApiError(Exception ex, HttpStatus status){
        ApiError apiError = ApiError
                .builder()
                .exception(ex)
                .message(ex.getMessage())
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
        return apiError;
    }
}
