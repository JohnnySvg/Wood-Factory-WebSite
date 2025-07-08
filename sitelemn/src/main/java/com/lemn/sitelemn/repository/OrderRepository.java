package com.lemn.sitelemn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemn.sitelemn.entity.Order;
import com.lemn.sitelemn.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	  List<Order> findByUserId(Long userId);
	  List<Order> findByUser(User user);
	}
