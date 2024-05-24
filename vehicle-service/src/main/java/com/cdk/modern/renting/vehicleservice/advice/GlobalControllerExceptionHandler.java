package com.cdk.modern.renting.vehicleservice.advice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  private static final String ERR_TEMPLATE = "error: {}, request:{}";

  @ExceptionHandler(WebExchangeBindException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleException(WebExchangeBindException e) {
    var errors =
        e.getFieldErrors().stream()
            .map(
                fieldError ->
                    String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
            .toList();
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(RuntimeException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleServerError(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Arrays.asList(ex.getMessage(), request.getContextPath());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(RestClientException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleGatewayError(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Arrays.asList(ex.getMessage(), request.getContextPath());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleConflict(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Collections.singletonList(ex.getMessage());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler({DataRetrievalFailureException.class, NoSuchElementException.class})
  public Mono<ResponseEntity<ErrorResponse>> handleNotFound(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Collections.singletonList(ex.getMessage());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND));
  }
}
