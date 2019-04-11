package com.kakaopay.housingfinance.common.exception.handler;

import com.kakaopay.housingfinance.common.exception.ApiException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return buildResponseEntity(ApiException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> nullPointerException(NullPointerException e) {
        e.printStackTrace();
        return buildResponseEntity(ApiException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(value = {NotFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<Object> notFoundUrl(NotFoundException e) {
        e.printStackTrace();
        return buildResponseEntity(ApiException.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> fileNotFoundException(FileNotFoundException e) {
        e.printStackTrace();
        return buildResponseEntity(ApiException.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e) {
        e.printStackTrace();
        ApiException build = ApiException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return buildResponseEntity(build);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeError(RuntimeException e) {
        e.printStackTrace();
        return buildResponseEntity(ApiException.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build());
    }





    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}
