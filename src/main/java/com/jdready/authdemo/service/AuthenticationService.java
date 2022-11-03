package com.jdready.authdemo.service;

import com.jdready.authdemo.model.User;

public interface AuthenticationService {
  User signInAndReturnJwt(User signInRequest);
}
