package com.jdready.gsmstrtxt.exceptions;

public class CustomPinTakenException extends IllegalArgumentException {
  public CustomPinTakenException() {
    super();
  }

  public CustomPinTakenException(String message) {
    super(message);
  }
}
