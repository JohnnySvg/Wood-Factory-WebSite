package com.lemn.sitelemn.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lemn.sitelemn.entity.CartItem;
import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.CartRepository;
import com.lemn.sitelemn.repository.ProductRepository;
import com.lemn.sitelemn.repository.UserRepository;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam int quantity,
                            Principal principal) {
        if (quantity < 1) {
            return "redirect:/products?error=invalid_quantity";
        }

        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        // Verificăm dacă există deja în coș
        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(user, product);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartRepository.save(newItem);
        }

        return "redirect:/products?success=added";
    }
    
    @Transactional
    @PostMapping("/cancel")
    public String cancelCart(Principal principal) {
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName()).orElseThrow();
            cartRepository.deleteByUser(user);
        }
        return "redirect:/products";
    }
}