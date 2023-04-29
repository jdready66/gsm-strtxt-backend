package com.jdready.gsmstrtxt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jdready.gsmstrtxt.model.Role;
import com.jdready.gsmstrtxt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByUsername(String username);

  public Optional<User> findByPin(String pin);
  
  @Modifying
  @Query("update User set role = :role where username = :username")
  void updateUserRole(@Param("username") String username, @Param("role") Role role);
}
