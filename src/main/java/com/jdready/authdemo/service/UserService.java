package com.jdready.authdemo.service;

import java.util.List;
import java.util.Optional;

import com.jdready.authdemo.model.Role;
import com.jdready.authdemo.model.User;

public interface UserService {
  User createUser(User userToCreate);
  Optional<User> findByUsername(String username);
  void changeRole(Role newRole, String username);
  List<User> findAllUsers();
  Optional<User> findById(long id);
  void deleteUser(long id);
  User updateUser(User userToSave);
}
