package com.example.explorecaliimages.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  
  /**
   * Leverage Exception Handler framework for unexpected Exceptions.
   * 
   * @param ex
   * @param request
   * @return
   */
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
    log.error("Caught Exception", ex);
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    return createResponseEntity(pd, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
