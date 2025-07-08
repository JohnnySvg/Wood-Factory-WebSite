package com.lemn.sitelemn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lemn.sitelemn.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	  List<Product> findByNameContainingIgnoreCase(String name);
	  List<Product> findByCategory(String category);
	  List<Product> findAllByOrderByPriceAsc();
	  List<Product> findAllByOrderByPriceDesc();
	  List<Product> findByCategoryOrderByPriceAsc(String category);
	  List<Product> findByCategoryOrderByPriceDesc(String category);

	}
