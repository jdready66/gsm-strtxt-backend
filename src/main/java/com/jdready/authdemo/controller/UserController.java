package com.jdready.authdemo.controller;

import java.util.Optional;

import com.jdready.authdemo.model.Role;
import com.jdready.authdemo.model.User;
import com.jdready.authdemo.security.UserPrincipal;
import com.jdready.authdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

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
}
