package com.jdready.authdemo.security.jwt;

import javax.servlet.http.HttpServletRequest;

import com.jdready.authdemo.security.UserPrincipal;

import org.springframework.security.core.Authentication;

public interface JwtProvider {
  public String generateToken(UserPrincipal auth);
  Authentication getAuthentication(HttpServletRequest request);
  boolean isTokenValid(HttpServletRequest request);
}
