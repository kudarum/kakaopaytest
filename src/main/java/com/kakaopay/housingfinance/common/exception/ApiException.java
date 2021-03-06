package com.kakaopay.housingfinance.common.exception;

import com.kakaopay.housingfinance.common.response.ApiResponseBody;
import com.kakaopay.housingfinance.common.response.ApiResponseMessage;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.FileNotFoundException;

@RestControllerAdvice
public class ApiException  {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity illegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseBody(HttpStatus.BAD_REQUEST,e.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity nullPointerException(NullPointerException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseBody(HttpStatus.BAD_REQUEST,e.getMessage()));
    }

    @ExceptionHandler(value = {NotFoundException.class, NoHandlerFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity notFoundUrl(NotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseBody(HttpStatus.NOT_FOUND,e.getMessage()));
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity fileNotFoundException(FileNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseBody(HttpStatus.NOT_FOUND,e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity fileSizeLimitExceededException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseBody(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_FILE_MAX_SIZE.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ResponseEntity responseStatusException(ResponseStatusException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponseBody(HttpStatus.FORBIDDEN,ApiResponseMessage.ERROR_UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity responseSignatureException(SignatureException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseBody(HttpStatus.BAD_REQUEST,e.getMessage()));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity notUnauthorizedAccess(UnsupportedJwtException e)
    {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseBody(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_NOT_RESOLVE_TOKEN.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity notUnauthorizedAccess(UsernameNotFoundException e)
    {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseBody(HttpStatus.BAD_REQUEST,ApiResponseMessage.ERROR_UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity runtimeError(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseBody(HttpStatus.INTERNAL_SERVER_ERROR,ApiResponseMessage.ERROR_RUN_TIME_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity exception(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseBody(HttpStatus.BAD_REQUEST,e.getMessage()));
    }

}
