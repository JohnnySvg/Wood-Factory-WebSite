package com.lemn.sitelemn.controller;


import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lemn.sitelemn.entity.OnsiteRequest;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.OnsiteRequestRepository;
import com.lemn.sitelemn.repository.UserRepository;

@Controller
public class ContactController {

	@Autowired
    private OnsiteRequestRepository onsiteRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/contact")
    public String contactPage(Model model, Principal principal) {
    	if (principal != null) {
            Optional<User> userOpt = userRepository.findByUsername(principal.getName());
            userOpt.ifPresent(user -> model.addAttribute("user", user));
        }
        return "contact";
    }

    @PostMapping("/onsite/apply")
    public String handleOnsiteRequest(@ModelAttribute OnsiteRequest onsiteRequest,
                                      Principal principal,
                                      RedirectAttributes redirectAttributes) {

        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null) {
                onsiteRequest.setUser(user);
            }
        }

        onsiteRequestRepository.save(onsiteRequest);
        redirectAttributes.addFlashAttribute("success", "Your service application was submitted successfully!");
        return "redirect:/contact";
    }
}

