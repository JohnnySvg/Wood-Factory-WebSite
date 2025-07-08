package com.lemn.sitelemn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemn.sitelemn.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	  List<Review> findByProductId(Long productId);
	}