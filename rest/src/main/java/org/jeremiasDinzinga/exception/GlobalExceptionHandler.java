package org.jeremiasDinzinga.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred", ex);
        return new ResponseEntity<>(
                Map.of("error", "An unexpected error occurred", "message", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.warn("Invalid argument provided", ex);
        return new ResponseEntity<>(
                Map.of("error", "Invalid argument", "message", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<Map<String, Object>> handleInterruptedException(InterruptedException ex, WebRequest request) {
        log.error("Thread was interrupted", ex);
        return new ResponseEntity<>(
                Map.of("error", "Request processing was interrupted", "message", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Map<String, Object>> handleArithmeticException(ArithmeticException ex, WebRequest request) {
        log.warn("Arithmetic error occurred", ex);
        return new ResponseEntity<>(
                Map.of("error", "Arithmetic error", "message", ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
