package com.jdready.gsmstrtxt.exceptions;

public class CustomUsernameTakenException extends IllegalArgumentException {
  public CustomUsernameTakenException() {
    super();
  }

  public CustomUsernameTakenException(String message) {
    super(message);
  }
}
