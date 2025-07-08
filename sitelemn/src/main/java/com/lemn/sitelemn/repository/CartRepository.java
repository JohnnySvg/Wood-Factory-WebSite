package com.lemn.sitelemn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemn.sitelemn.entity.CartItem;
import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.User;

public interface CartRepository extends JpaRepository<CartItem, Long> {
	  List<CartItem> findByUser(User user);
	  void deleteByUser(User user);
	  Optional<CartItem> findByUserAndProduct(User user, Product product);
	}