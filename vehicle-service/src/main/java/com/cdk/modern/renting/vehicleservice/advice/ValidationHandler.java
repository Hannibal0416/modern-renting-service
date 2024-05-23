package com.cdk.modern.renting.vehicleservice.advice;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;

@ControllerAdvice
public class ValidationHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  public ResponseEntity<ErrorResponse> handleException(WebExchangeBindException e) {
    var errors =
        e.getFieldErrors().stream()
            .map(
                fieldError ->
                    String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
            .toList();
    return ResponseEntity.badRequest().body(ErrorResponse.builder().errors(errors).build());
  }
}
