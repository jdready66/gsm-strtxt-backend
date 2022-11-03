package com.jdready.authdemo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column(name="name", nullable=false)
  private String name;

  @Column(name="username", nullable=false, unique=true, length=100)
  private String username;

  @Column(name="password", nullable=false)
  private String password;

  @Column(name="create_date", nullable=false)
  private LocalDateTime createDate;

  @Enumerated(EnumType.STRING)
  @Column(name="role", nullable=false)
  private Role role;

  @Transient
  private String accessToken;

  @Transient
  private String refreshToken;
}
