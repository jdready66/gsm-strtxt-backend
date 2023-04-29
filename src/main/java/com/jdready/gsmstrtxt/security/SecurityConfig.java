package com.jdready.gsmstrtxt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jdready.gsmstrtxt.model.Role;
import com.jdready.gsmstrtxt.security.jwt.JwtAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  public CustomUserDetailsService userDetailsService;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.cors().and()
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll() // Allow anything
            .antMatchers("/api/admin/update-password/{id}").access("hasRole('ADMIN') or @accessHandler.isSelf(authentication, #id)")
            .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name()) // ONLY ADMINs
            .anyRequest().authenticated() // ALL other requests must be authenticated
            .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*");
      }
    };
  }
}
