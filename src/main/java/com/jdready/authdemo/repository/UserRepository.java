package com.jdready.authdemo.repository;

import java.util.Optional;

import com.jdready.authdemo.model.Role;
import com.jdready.authdemo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
  public Optional<User> findByUsername(String username);
  
  @Modifying
  @Query("update User set role = :role where username = :username")
  void updateUserRole(@Param("username") String username, @Param("role") Role role);
}
