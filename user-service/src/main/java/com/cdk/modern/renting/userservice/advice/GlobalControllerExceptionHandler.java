package com.cdk.modern.renting.userservice.advice;

import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
class GlobalControllerExceptionHandler {

  private static final String ERR_TEMPLATE = "error: {}, request:{}";

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  private Map<String, List<String>> getErrorsMap(List<String> errors) {
    Map<String, List<String>> errorResponse = new HashMap<>();
    errorResponse.put("errors", errors);
    return errorResponse;
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, List<String>>> handleServerError(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Arrays.asList(ex.getMessage(), request.getContextPath());
    return new ResponseEntity<>(
        getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RestClientException.class)
  public ResponseEntity<Map<String, List<String>>> handleGatewayError(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Arrays.asList(ex.getMessage(), request.getContextPath());
    return new ResponseEntity<>(
        getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_GATEWAY);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, List<String>>> handleConflict(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler({DataRetrievalFailureException.class, NoSuchElementException.class})
  public ResponseEntity<Map<String, List<String>>> handleNotFound(
      RuntimeException ex, WebRequest request) {
    log.error(ERR_TEMPLATE, ex, request);
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

}
