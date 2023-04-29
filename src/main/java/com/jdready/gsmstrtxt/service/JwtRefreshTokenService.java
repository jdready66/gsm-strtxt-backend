package com.jdready.gsmstrtxt.service;

import com.jdready.gsmstrtxt.model.JwtRefreshToken;
import com.jdready.gsmstrtxt.model.User;

public interface JwtRefreshTokenService {
  JwtRefreshToken createRefreshToken(Long userId);
  User generateAccessTokenFromRefreshToken(String refreshTokenId);
}
