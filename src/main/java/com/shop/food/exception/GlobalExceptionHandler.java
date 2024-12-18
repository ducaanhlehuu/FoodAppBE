package com.shop.food.exception;


import com.shop.food.entity.response.ResponseBody;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseBody> handleUnauthorizedException(UnauthorizedException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode(ResponseBody.UNAUTHORIZED);
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseBody> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode(ResponseBody.BAD_REQUEST);
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseBody> handleUserNotFoundException(UserNotFoundException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode(ResponseBody.BAD_REQUEST);
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseBody> handleNoSuchElementException(NoSuchElementException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode(ResponseBody.BAD_REQUEST);
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseBody> handleUserNotFoundException(IllegalArgumentException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode(ResponseBody.BAD_REQUEST);
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ResponseBody> handleAuthenticationException(AuthenticationException ex) {
//        ResponseBody responseBody = new ResponseBody();
//        responseBody.setResultMessage("Tài khoản hoặc mật khẩu không phù hợp");
//        responseBody.setResultCode(ResponseBody.BAD_REQUEST);
//        responseBody.setData(null);
//
//        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.put("message", "JWT token has expired. Please login again.");
        response.put("details", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(com.fasterxml.jackson.core.JsonParseException.class)
    public ResponseEntity<?> handleInvalidJson(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", "Invalid JSON format");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", "Validation error");
        errorDetails.put("message", errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
