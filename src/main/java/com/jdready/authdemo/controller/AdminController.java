package com.jdready.authdemo.controller;

import java.util.Optional;

import com.jdready.authdemo.model.User;
import com.jdready.authdemo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {
  @Autowired
  private UserService userService;

  @GetMapping("all")
  public ResponseEntity<?> findAllUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  @PutMapping("update-user")
  public ResponseEntity<?> updateUser(@RequestBody User user) {
    Optional<User> checkUser = userService.findById(user.getId());
    if (checkUser.isPresent()) {
      return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("delete-user/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(true);
  }
}
