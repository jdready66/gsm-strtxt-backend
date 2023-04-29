package com.jdready.gsmstrtxt.service;

import java.util.List;
import java.util.Optional;

import com.jdready.gsmstrtxt.model.Role;
import com.jdready.gsmstrtxt.model.User;

public interface UserService {
  User createUser(User userToCreate) throws Exception;
  Optional<User> findByUsername(String username);

  Optional<User> findByPin(String pin);

  void changeRole(Role newRole, String username);
  List<User> findAllUsers();
  Optional<User> findById(long id);
  void deleteUser(long id);
  User updateUser(User userToSave) throws Exception;
  User updatePassword(User userToSave);
}
