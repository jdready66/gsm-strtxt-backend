package com.jdready.gsmstrtxt.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdready.gsmstrtxt.model.User;
import com.jdready.gsmstrtxt.service.UserService;
import com.jdready.gsmstrtxt.utils.SecurityUtils;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

    Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(user.getRole().name()));
    return UserPrincipal.builder()
        .user(user)
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(authorities)
        .build();
  }
}
