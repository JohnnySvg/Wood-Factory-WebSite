package com.lemn.sitelemn.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lemn.sitelemn.entity.CartItem;
import com.lemn.sitelemn.entity.Order;
import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.CartRepository;
import com.lemn.sitelemn.repository.OnsiteRequestRepository;
import com.lemn.sitelemn.repository.OrderRepository;
import com.lemn.sitelemn.repository.ProductRepository;
import com.lemn.sitelemn.repository.UserRepository;
import com.lemn.sitelemn.service.OrderService;

@Controller
public class OrderController {

    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private OrderService orderService;

    
    

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                                  .orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> cartItems = cartRepository.findByUser(user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Cart is empty.");
        }
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("totalPrice", total);
        
        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(@RequestParam String address,
                             @RequestParam String phone,
                             Principal principal) {

        User user = userRepository.findByUsername(principal.getName())
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            orderService.placeOrder(user, address, phone);
            return "redirect:/cont?success=order";
        } catch (IllegalStateException e) {
            return "redirect:/checkout?error=" + e.getMessage();
        }
    }
    
    
}