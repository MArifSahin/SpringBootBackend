package com.innova.service;

import com.innova.model.Role;
import com.innova.model.User;
import com.innova.security.services.UserDetailImpl;

import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.Set;


public interface UserService {
    public User getUserWithAuthentication(Authentication authentication);
    public User editUser(User user, String name, String lastName, String age, String phoneNumber);
    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
    public Optional<User> findByEmail(String email);
    public User changeEmail(User user, String email);
    public UserDetailImpl getUserDetails(Authentication authentication);
    public User setNewPassword(User user, String password);
    public User getUserByEmail(String email);
    public User getUserByToken(String token, String matter);
    public Optional<User> findUserByUsername(String username);
    public User saveUser(User user);
    public Optional<User> findUserByRole(Set<Role> roles);
}
