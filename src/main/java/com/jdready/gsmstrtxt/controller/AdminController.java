package com.jdready.gsmstrtxt.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdready.gsmstrtxt.exceptions.CustomUsernameTakenException;
import com.jdready.gsmstrtxt.model.PasswordChange;
import com.jdready.gsmstrtxt.model.User;
import com.jdready.gsmstrtxt.service.UserService;

@RestController
@RequestMapping("api/admin")
public class AdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("all")
  public ResponseEntity<?> findAllUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  @PutMapping("update-user/{id}")
  public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user) throws Exception, CustomUsernameTakenException {
    if (id != user.getId().longValue()) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    Optional<User> checkUser = userService.findById(user.getId());
    if (checkUser.isPresent()) {
      return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("update-password/{id}")
  public ResponseEntity<?> updatePassword(@PathVariable long id, @RequestBody PasswordChange pwChange, Authentication authentication) {
    boolean hasAdminRole = authentication.getAuthorities().stream()
        .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

    User user = pwChange.getUser();
    if (id != user.getId().longValue()) {
      return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }

    Optional<User> checkUser = userService.findById(user.getId());
    if (checkUser.isPresent()) {
      if (!hasAdminRole) {
        if (pwChange.getCurrentPassword() == null || pwChange.getCurrentPassword().isBlank()) {
          return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!passwordEncoder.matches(pwChange.getCurrentPassword(), checkUser.get().getPassword())) {
          return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
      }

      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return new ResponseEntity<>(userService.updatePassword(user), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("delete-user/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(true);
  }
}
