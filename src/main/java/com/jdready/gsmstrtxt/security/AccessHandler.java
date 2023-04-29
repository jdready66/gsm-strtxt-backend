package com.jdready.gsmstrtxt.security;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.jdready.gsmstrtxt.model.User;
import com.jdready.gsmstrtxt.repository.UserRepository;


@Component("accessHandler")
public class AccessHandler {

  @Autowired
  private UserRepository userRepository;
  
  public boolean isSelf(Authentication authentication, long userId) {
    if (authentication.isAuthenticated()) {
      Optional<User> possibleUser = userRepository.findByUsername(authentication.getName());
      if (possibleUser.isPresent()) {
        User user = possibleUser.get();
        if (user.getId().longValue() == userId) {
          return true;
        }
      }
    }
    return false;
  }
}
