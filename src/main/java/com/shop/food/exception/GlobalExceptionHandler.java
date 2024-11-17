package com.shop.food.exception;


import com.shop.food.entity.response.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseBody> handleUnauthorizedException(UnauthorizedException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode("401");
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseBody> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode("400");
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseBody> handleUserNotFoundException(UserNotFoundException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode("400");
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseBody> handleUserNotFoundException(IllegalArgumentException ex) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setResultMessage(ex.getMessage());
        responseBody.setResultCode("401");
        responseBody.setData(null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
