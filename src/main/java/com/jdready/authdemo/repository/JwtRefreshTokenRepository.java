package com.jdready.authdemo.repository;

import com.jdready.authdemo.model.JwtRefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {
}
