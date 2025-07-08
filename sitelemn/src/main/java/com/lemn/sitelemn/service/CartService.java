package com.lemn.sitelemn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemn.sitelemn.entity.CartItem;
import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.CartRepository;
import com.lemn.sitelemn.repository.ProductRepository;

@Service
public class CartService {
  @Autowired private CartRepository cartRepository;
  @Autowired private ProductRepository productRepository;

  public List<CartItem> getCartItems(User user) {
    return cartRepository.findByUser(user);
  }

  public void addToCart(User user, Product product, int quantity) {
    CartItem item = new CartItem();
    item.setUser(user);
    item.setProduct(product);
    item.setQuantity(quantity);
    cartRepository.save(item);
  }

  public void clearCart(User user) {
    cartRepository.deleteByUser(user);
  }
}
