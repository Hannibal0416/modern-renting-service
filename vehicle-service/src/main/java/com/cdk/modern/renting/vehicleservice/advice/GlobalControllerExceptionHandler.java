package com.cdk.modern.renting.vehicleservice.advice;

import org.springframework.security.access.AccessDeniedException;
import java.util.Arrays;
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
import org.springframework.web.server.ServerWebExchange;
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
      RuntimeException ex, ServerWebExchange exchange) {
    log.error(ERR_TEMPLATE, ex, exchange);
    List<String> errors = Arrays.asList(ex.getMessage(), exchange.getRequest().getPath().toString());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR));
  }

  @ExceptionHandler(RestClientException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleGatewayError(
      RuntimeException ex, ServerWebExchange exchange) {
    log.error(ERR_TEMPLATE, ex, exchange);
    List<String> errors = Arrays.asList(ex.getMessage(), exchange.getRequest().getPath().toString());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.BAD_REQUEST));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleConflict(
      RuntimeException ex, ServerWebExchange exchange) {
    log.error(ERR_TEMPLATE, ex, exchange);
    List<String> errors = Arrays.asList(ex.getMessage(), exchange.getRequest().getPath().toString());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.CONFLICT));
  }

  @ExceptionHandler({DataRetrievalFailureException.class, NoSuchElementException.class})
  public Mono<ResponseEntity<ErrorResponse>> handleNotFound(
      RuntimeException ex, ServerWebExchange exchange) {
    log.error(ERR_TEMPLATE, ex, exchange);
    List<String> errors = Arrays.asList(ex.getMessage(), exchange.getRequest().getPath().toString());
    return Mono.just(
        new ResponseEntity<>(
            ErrorResponse.builder().errors(errors).build(),
            new HttpHeaders(),
            HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler({AccessDeniedException.class})
  public Mono<ResponseEntity<ErrorResponse>> handleNotFound(
          AccessDeniedException ex, ServerWebExchange exchange) {
    log.error(ERR_TEMPLATE, ex, exchange);
    List<String> errors = Arrays.asList(ex.getMessage(), exchange.getRequest().getPath().toString());
    return Mono.just(
            new ResponseEntity<>(
                    ErrorResponse.builder().errors(errors).build(),
                    new HttpHeaders(),
                    HttpStatus.FORBIDDEN));
  }
}
