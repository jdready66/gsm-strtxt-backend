package com.jdready.gsmstrtxt.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdready.gsmstrtxt.exceptions.CustomPinTakenException;
import com.jdready.gsmstrtxt.exceptions.CustomUsernameTakenException;
import com.jdready.gsmstrtxt.model.Role;
import com.jdready.gsmstrtxt.model.User;
import com.jdready.gsmstrtxt.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

  private static final String USERNAME_TAKEN_TEXT = "constraint [users.UC_username]";
  private static final String PIN_TAKEN_TEXT = "constraint [users.UC_pin]";

  @Autowired
  private UserRepository userRepository;

  @Override
  public User createUser(User userToCreate) throws Exception, CustomUsernameTakenException {
    LOG.info("createUser {}", userToCreate);
    userToCreate.setRole(Role.USER);
    userToCreate.setCreateDate(LocalDateTime.now());

    User createdUser = null;
    try {
      createdUser = userRepository.save(userToCreate);
    } catch (DataIntegrityViolationException ex) {
        processDataIntegrityViolationException(ex, userToCreate.getUsername(), userToCreate.getPin(), "creating");
    } catch (Exception ex) {
      LOG.error("Problem in createdUser(): " + ex.getMessage());
      throw new Exception("Problem creating user.");
    }

    return createdUser;
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
  public User updateUser(User userDto) throws Exception, CustomUsernameTakenException {
    LOG.info("updateUser: {}", userDto);

    Optional<User> user = findById(userDto.getId());
    if (user.isPresent()) {
      User userToSave = user.get();
      userToSave.setPin(userDto.getPin());
      userToSave.setName(userDto.getName());
      userToSave.setUsername(userDto.getUsername());
      userToSave.setRole(userDto.getRole());
      User savedUser = null;
      try {
        savedUser = userRepository.save(userToSave);
      }
      catch(DataIntegrityViolationException ex) {
        processDataIntegrityViolationException(ex, userDto.getUsername(), userDto.getPin(), "saving");
      }
      catch(Exception ex) {
        LOG.error("Unknown problem saving user: " + ex.getMessage());
        throw new Exception("Problem saving user.");
      }
      return savedUser;
    }

    return null;
  }

  private void processDataIntegrityViolationException(DataIntegrityViolationException ex, 
      String username,
      String pin,
      String action)
      throws Exception, CustomUsernameTakenException {

    String msg = ex.getMessage();
    if (msg != null && msg.contains(USERNAME_TAKEN_TEXT)) {
      throw new CustomUsernameTakenException(String.format("Username: '%s' already taken", username));
    } else if (msg != null && msg.contains(PIN_TAKEN_TEXT)) {
      throw new CustomPinTakenException(String.format("Pin: '%s' already taken", pin));
    } else {
      LOG.error("Unknown data integrity problem " + action + " user: " + ex.getMessage());
      throw new Exception("Problem " + action + " user.");
    }
  }

  @Override
  public User updatePassword(User userDto) {
    Optional<User> user = findById(userDto.getId());
    if (user.isPresent()) {
      User userToSave = user.get();
      userToSave.setPassword(userDto.getPassword());
      return userRepository.save(userToSave);
    }

    return null;
  }

  @Override
  public Optional<User> findByPin(String pin) {
    return userRepository.findByPin(pin);
  }

}