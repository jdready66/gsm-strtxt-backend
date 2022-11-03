package com.jdready.authdemo.service;

import com.jdready.authdemo.model.JwtRefreshToken;
import com.jdready.authdemo.model.User;

public interface JwtRefreshTokenService {
  JwtRefreshToken createRefreshToken(Long userId);
  User generateAccessTokenFromRefreshToken(String refreshTokenId);
}
