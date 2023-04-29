package com.jdready.gsmstrtxt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jdready.gsmstrtxt.model.User;
import com.jdready.gsmstrtxt.service.AuthenticationService;
import com.jdready.gsmstrtxt.service.JwtRefreshTokenService;
import com.jdready.gsmstrtxt.service.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("sign-up")
  public ResponseEntity<?> signUp(@RequestBody User user) throws Exception {
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
