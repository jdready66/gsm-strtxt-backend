package com.jdready.gsmstrtxt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PasswordChange {
  private User user;
  private String currentPassword;
}
