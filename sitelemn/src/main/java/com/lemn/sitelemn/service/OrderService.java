package com.lemn.sitelemn.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lemn.sitelemn.entity.CartItem;
import com.lemn.sitelemn.entity.Order;
import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.CartRepository;
import com.lemn.sitelemn.repository.OrderRepository;
import com.lemn.sitelemn.repository.ProductRepository;

@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartRepository cartRepository;

    @Transactional
    public Order placeOrder(User user, String address, String phone) {
        List<CartItem> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is Empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPhone(phone);
        order.setDate(LocalDateTime.now());
        order.setStatus("IN_ASTEPTARE");

       

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalStateException("Not enough in stock for: " + product.getName());
            }

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            
        }

        orderRepository.save(order);

        cartRepository.deleteByUser(user);

        return order;
    }
}