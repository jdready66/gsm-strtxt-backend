package com.jdready.gsmstrtxt.service;

import com.jdready.gsmstrtxt.model.User;

public interface AuthenticationService {
  User signInAndReturnJwt(User signInRequest);
}
