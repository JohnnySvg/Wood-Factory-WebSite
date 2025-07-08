package com.lemn.sitelemn.controller;

import com.lemn.sitelemn.entity.Order;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.OnsiteRequestRepository;
import com.lemn.sitelemn.repository.OrderRepository;
import com.lemn.sitelemn.repository.UserRepository;
import com.lemn.sitelemn.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
	
	@Autowired
    private OnsiteRequestRepository onsiteRequestRepository;
	
	@Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired private OrderRepository orderRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "The Username is taken!");
            return "register";
        }
        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole("CLIENT");
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/cont")
    public String contPage(Model model, Principal principal) {
        Optional<User> userOpt = userRepository.findByUsername(principal.getName());
        userOpt.ifPresent(user -> model.addAttribute("user", user));
        
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

List<Order> orders = orderRepository.findByUser(user);

model.addAttribute("orders", orders);
if (user != null) {
    
    model.addAttribute("onsiteRequests", onsiteRequestRepository.findByUser(user));
}

        return "cont";
    }

    @PostMapping("/cont/update")
    public String updateCont(@ModelAttribute User updatedUser, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setAddress(updatedUser.getAddress());

        userRepository.save(user);
        return "redirect:/cont";
    }
    
    @GetMapping("/cont/date-personale")
    public String showEditProfileForm(Model model, Principal principal) {
    	User user = userService.findByUsername(principal.getName()).orElseThrow();
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/cont/date-personale")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
    	User existingUser = userService.findById(updatedUser.getId()).orElseThrow();

        // validări
        if (!newPassword.isBlank()) {
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Passwords do not match!");
                return "edit-profile";
            }
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setUsername(updatedUser.getUsername());

        userService.save(existingUser);

        model.addAttribute("success", "Details updated succesfully.");
        return "redirect:/cont";
    }
}