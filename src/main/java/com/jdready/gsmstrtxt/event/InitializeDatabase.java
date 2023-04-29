package com.jdready.gsmstrtxt.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jdready.gsmstrtxt.model.Role;
import com.jdready.gsmstrtxt.model.User;
import com.jdready.gsmstrtxt.service.UserService;

@Component
public class InitializeDatabase {
  private static Logger LOG = LoggerFactory.getLogger(InitializeDatabase.class);

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @EventListener(ApplicationReadyEvent.class)
  public void EventListenerExecute() {
    LOG.info("Application Ready Event is successfully Started");

    List<User> userList = userService.findAllUsers();
    if (userList.size() > 0) {
      LOG.info("users found...  skipping initialization");
    } else {
      LOG.info("No users found... Adding initial admin user");
      User admin = new User();
      admin.setName("admin");
      admin.setPin("1234");
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("admin"));
      try {
        userService.createUser(admin);
        admin.setRole(Role.ADMIN);
        userService.updateUser(admin);
        LOG.info("admnin user created successfully");
      } catch (Exception e) {
        LOG.error("Problem creating initial admin user");
        LOG.error(e.getMessage());
      }
    }
  }
}
