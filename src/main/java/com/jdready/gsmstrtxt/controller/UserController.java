package com.jdready.gsmstrtxt.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdready.gsmstrtxt.model.Role;
import com.jdready.gsmstrtxt.model.User;
import com.jdready.gsmstrtxt.security.UserPrincipal;
import com.jdready.gsmstrtxt.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
  private static Logger LOG = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @GetMapping("{id}")
  public ResponseEntity<?> getUserById(@PathVariable long id) {
    Optional<User> user = userService.findById(id);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("change/{role}")
  public ResponseEntity<?> changeRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
      @PathVariable Role role) {
    userService.changeRole(role, userPrincipal.getUsername());

    return ResponseEntity.ok(true);
  }

  @GetMapping("/pin/{pin}")
  public ResponseEntity<?> getUserByPin(@PathVariable String pin) {
    LOG.info("getUserByPin(), pin: " + pin);

    Optional<User> user = userService.findByPin(pin);
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
