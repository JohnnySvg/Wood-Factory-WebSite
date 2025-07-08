package com.lemn.sitelemn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.UserRepository;

@Service
public class UserService {
  @Autowired private UserRepository userRepository;
  @Autowired private BCryptPasswordEncoder passwordEncoder;

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
  
  

  public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("CLIENT");
    return userRepository.save(user);
  }

  public void updateUser(User updatedUser) {
    Optional<User> userOpt = userRepository.findById(updatedUser.getId());
    if (userOpt.isPresent()) {
      User user = userOpt.get();
      user.setFullName(updatedUser.getFullName());
      user.setEmail(updatedUser.getEmail());
      user.setPhone(updatedUser.getPhone());
      user.setAddress(updatedUser.getAddress());
      userRepository.save(user);
    }
  }
  

  public Optional<User> findById(Long id) {
      return userRepository.findById(id);
  }

  public User save(User user) {
      return userRepository.save(user);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}

