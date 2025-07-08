package com.lemn.sitelemn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemn.sitelemn.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	  Optional<User> findByUsername(String username);
	  
	}
