package com.jdready.gsmstrtxt.controller;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
  private Map<String,String> errors;

  public ErrorResponse(String target, String message) {
    this.errors = new HashMap<String, String>();
    this.errors.put(target, message);
  }
}
