package com.lemn.sitelemn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.repository.ProductRepository;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public void updateStock(Long productId, int newStock) {
    Product product = productRepository.findById(productId)
      .orElseThrow(() -> new RuntimeException("Product not found"));

    product.setStock(newStock);
    productRepository.save(product);
  }

  public void decreaseStock(Product product, int quantity) {
    int currentStock = product.getStock();
    if (currentStock < quantity) {
      throw new IllegalArgumentException("Not enough stock for : " + product.getName());
    }

    product.setStock(currentStock - quantity);
    productRepository.save(product);
  }
}