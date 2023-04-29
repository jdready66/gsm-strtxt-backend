package com.jdready.gsmstrtxt.security.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.jdready.gsmstrtxt.security.UserPrincipal;

public interface JwtProvider {
  public String generateToken(UserPrincipal auth);
  Authentication getAuthentication(HttpServletRequest request);
  boolean isTokenValid(HttpServletRequest request);
}
