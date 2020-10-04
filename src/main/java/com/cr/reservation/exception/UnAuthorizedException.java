package com.cr.reservation.exception;

public class UnAuthorizedException extends RuntimeException {

  /**
   * Constructs an {@code UnAuthorizedException} with the specified message and root cause.
   *
   * @param msg the detail message
   * @param t   the root cause
   */
  public UnAuthorizedException(String msg, Throwable t) {
    super(msg, t);
  }

  /**
   * Constructs an {@code UnAuthorizedException} with the specified message and no root cause.
   *
   * @param msg the detail message
   */
  public UnAuthorizedException(String msg) {
    super(msg);
  }

}
