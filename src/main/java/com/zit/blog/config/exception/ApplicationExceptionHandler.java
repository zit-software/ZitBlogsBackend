package com.zit.blog.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> customExceptionHandler(CustomException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("message", exception.getMessage());
        return new ResponseEntity<>(error, exception.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleRequestBodyError(HttpMessageNotReadableException exception) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Vui lòng kiểm tra lại thông tin");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundHandler(NoHandlerFoundException noHandlerFoundException) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Route " + noHandlerFoundException.getRequestURL() + " không tồn tại");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
