package com.lemn.sitelemn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lemn.sitelemn.entity.Order;
import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.repository.OnsiteRequestRepository;
import com.lemn.sitelemn.repository.OrderRepository;
import com.lemn.sitelemn.repository.ProductRepository;
import com.lemn.sitelemn.repository.UserRepository;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	@Autowired
    private OnsiteRequestRepository onsiteRequestRepository;
  @Autowired ProductRepository productRepository;
  @Autowired OrderRepository orderRepository;
  @Autowired UserRepository userRepository;

  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    model.addAttribute("products", productRepository.findAll());
    model.addAttribute("orders", orderRepository.findAll());
    model.addAttribute("users", userRepository.findAll());
    return "admin-dashboard";
  }
  
  @GetMapping("/orders")
  public String adminOrders(Model model) {
      model.addAttribute("orders", orderRepository.findAll());
      model.addAttribute("onsiteRequests", onsiteRequestRepository.findAll());
      return "admin-orders";
  }

  @GetMapping("/users")
  public String adminUsers(Model model) {
      model.addAttribute("users", userRepository.findAll());
      return "admin-users";
  }
  
  @GetMapping("/admin/orders")
  public String showOrders(Model model) {
      model.addAttribute("orders", orderRepository.findAll());
      return "admin/admin-orders";
  }
  
  @GetMapping("/admin/users")
  public String showUsers(Model model) {
      model.addAttribute("users", userRepository.findAll());
      return "admin/admin-users";
  }

  @PostMapping("/products/{id}/stock")
  public String updateStock(@PathVariable Long id, @RequestParam int stock) {
    Product p = productRepository.findById(id).orElseThrow();
    p.setStock(stock);
    productRepository.save(p);
    return "redirect:/admin/dashboard";
  }

  @PostMapping("/users/{id}/delete")
  public String deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
    return "redirect:/admin/dashboard";
  }

  @PostMapping("/orders/{id}/livrat")
  public String markDelivered(@PathVariable Long id) {
    Order order = orderRepository.findById(id).orElseThrow();
    order.setStatus("LIVRAT");
    orderRepository.save(order);
    return "redirect:/admin/orders";
    }
    
   @PostMapping("/onsite/{id}/delete")
   public String deleteOnsiteRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        onsiteRequestRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Request deleted successfully.");
        return "redirect:/admin/orders";
    
  }
}
