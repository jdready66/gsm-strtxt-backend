package com.jdready.gsmstrtxt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jdready.gsmstrtxt.exceptions.CustomPinTakenException;
import com.jdready.gsmstrtxt.exceptions.CustomUsernameTakenException;

@ControllerAdvice
public class CustomControllerAdvice {
  
  @ExceptionHandler(CustomUsernameTakenException.class)
  public ResponseEntity<ErrorResponse> handleCustomUsernameTakenException(Exception e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ErrorResponse errorResponse = new ErrorResponse("username", e.getMessage());
    return new ResponseEntity<ErrorResponse>(
        errorResponse,
        status);
  }

  @ExceptionHandler(CustomPinTakenException.class)
  public ResponseEntity<ErrorResponse> handleCustomPinTakenException(Exception e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ErrorResponse errorResponse = new ErrorResponse("pin", e.getMessage());
    return new ResponseEntity<ErrorResponse>(
        errorResponse,
        status);
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return new ResponseEntity<ErrorResponse>(
        new ErrorResponse("internal-server", e.getMessage()),
        status);

  }

}
