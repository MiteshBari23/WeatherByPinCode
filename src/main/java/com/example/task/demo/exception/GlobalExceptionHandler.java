package com.example.task.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongPincodeException.class)
    public ResponseEntity<Map<String, Object>>handlePincode(WrongPincodeException ex){
        Map<String , Object> err = new HashMap<>();

        err.put("timestamp", LocalDateTime.now());
        err.put("message", ex.getMessage());
        err.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<Map<String, Object>> WeatherApiException(WeatherApiException ex){
        Map<String , Object> err = new HashMap<>();

        err.put("timestamp", LocalDateTime.now());
        err.put("message", ex.getMessage());
        err.put("status", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
    // fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
