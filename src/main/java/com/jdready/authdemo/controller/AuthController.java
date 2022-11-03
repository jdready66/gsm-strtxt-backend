package com.jdready.authdemo.controller;

import com.jdready.authdemo.model.User;
import com.jdready.authdemo.service.AuthenticationService;
import com.jdready.authdemo.service.JwtRefreshTokenService;
import com.jdready.authdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("sign-up")
  public ResponseEntity<?> signUp(@RequestBody User user) {
    if (userService.findByUsername(user.getUsername()).isPresent()) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("sign-in")
  public ResponseEntity<?> signIn(@RequestBody User user) {
    return new ResponseEntity<>(authenticationService.signInAndReturnJwt(user), HttpStatus.OK);
  }

  @Autowired
  private JwtRefreshTokenService jwtRefreshTokenService;

  @PostMapping("refresh-token")
  public ResponseEntity<?> refreshToken(@RequestParam String token) {
    return ResponseEntity.ok(jwtRefreshTokenService.generateAccessTokenFromRefreshToken(token));
  }
}
