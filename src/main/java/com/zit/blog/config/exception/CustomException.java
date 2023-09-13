package com.zit.blog.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CustomException extends Exception {
    private String message;
    private HttpStatus httpStatus;
}
