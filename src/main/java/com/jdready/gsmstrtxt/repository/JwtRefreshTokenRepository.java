package com.jdready.gsmstrtxt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdready.gsmstrtxt.model.JwtRefreshToken;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {
}
