package com.spring.jwt;

import com.spring.jwt.domain.Role;
import com.spring.jwt.domain.User;
import com.spring.jwt.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

/**
 * 인증
 * Authentication => Verifies you are who you say you are
 *   Method
 *     - Login form
 *     - HTTP authentication
 *     - Custom auth. method
 */

/**
 * 인가 (허가)
 * Authorization  => Decides if you have permission to access a resource
 *   Method
 *     - Access Control URLs
 *     - Access Control List (ACLs)
 */
@SpringBootApplication
public class SpringBootJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJwtApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
          userService.saveRole(new Role(null, "ROLE_USER"));
          userService.saveRole(new Role(null, "ROLE_MANAGER"));
          userService.saveRole(new Role(null, "ROLE_ADMIN"));
          userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

          userService.saveUser(new User(null, "John Travolta", "john", "1234", new ArrayList<>()));
          userService.saveUser(new User(null, "Will Smith", "will", "1234", new ArrayList<>()));
          userService.saveUser(new User(null, "Jim Carry", "jim", "1234", new ArrayList<>()));
          userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>()));
          userService.saveUser(new User(null, "Jong Woo", "jw", "1234", new ArrayList<>()));

          userService.addRoleToUser("john", "ROLE_USER");
          userService.addRoleToUser("will", "ROLE_MANAGER");
          userService.addRoleToUser("jim", "ROLE_ADMIN");
          userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
          userService.addRoleToUser("arnold", "ROLE_ADMIN");
          userService.addRoleToUser("arnold", "ROLE_USER");
          userService.addRoleToUser("jw", "ROLE_USER");
          userService.addRoleToUser("jw", "ROLE_MANAGER");
          userService.addRoleToUser("jw", "ROLE_ADMIN");
          userService.addRoleToUser("jw", "ROLE_SUPER_ADMIN");
        };
    }
}
