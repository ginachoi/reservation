package com.cr.reservation.exception;

import java.time.LocalDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ReservationExceptionAdvice {

  private static final Logger log = LogManager.getLogger(ReservationExceptionAdvice.class);


  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
    ErrorResponse error = new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage());
    error.setTimestamp(LocalDateTime.now());
    error.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = UnAuthorizedException.class)
  public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
    ErrorResponse error = new ErrorResponse("UNAUTHORIZED", e.getMessage());
    error.setTimestamp(LocalDateTime.now());
    error.setStatus((HttpStatus.UNAUTHORIZED.value()));
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
    ErrorResponse error = new ErrorResponse("BAD_REQUEST", e.getMessage());
    error.setTimestamp(LocalDateTime.now());
    error.setStatus((HttpStatus.BAD_REQUEST.value()));
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
