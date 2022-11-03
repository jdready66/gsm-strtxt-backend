package com.jdready.authdemo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.jdready.authdemo.model.Role;
import com.jdready.authdemo.model.User;
import com.jdready.authdemo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User createUser(User userToCreate) {
    userToCreate.setRole(Role.USER);
    userToCreate.setCreateDate(LocalDateTime.now());

    return userRepository.save(userToCreate);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  @Transactional
  public void changeRole(Role newRole, String username) {
    userRepository.updateUserRole(username, newRole);
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public void deleteUser(long id) {
    Optional<User> user = findById(id);
    if (user.isPresent()) {
      userRepository.delete(user.get());
    }    
  }

  @Override
  public User updateUser(User userDto) {
    Optional<User> user = findById(userDto.getId());
    if (user.isPresent()) {
      User userToSave = user.get();
      userToSave.setName(userDto.getName());
      userToSave.setUsername(userDto.getUsername());
      userToSave.setRole(userDto.getRole());
      return userRepository.save(userToSave);
    }

    return null;
  }
}