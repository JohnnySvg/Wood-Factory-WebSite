package com.lemn.sitelemn.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.Review;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.ProductRepository;
import com.lemn.sitelemn.repository.ReviewRepository;
import com.lemn.sitelemn.repository.UserRepository;

@Controller
@RequestMapping("/products")
public class ProductController {
  @Autowired ProductRepository productRepository;
  @Autowired ReviewRepository reviewRepository;
  @Autowired UserRepository userRepository;

  @GetMapping
  public String list(@RequestParam(required = false) String category,
                     @RequestParam(required = false) String sort,
                     Model model) {

      List<Product> products;

      boolean hasCategory = category != null && !category.isBlank();
      boolean hasSort = sort != null && !sort.isBlank();

      if (hasCategory && hasSort) {
          if (sort.equals("asc")) {
              products = productRepository.findByCategoryOrderByPriceAsc(category);
          } else if (sort.equals("desc")) {
              products = productRepository.findByCategoryOrderByPriceDesc(category);
          } else {
              products = productRepository.findByCategory(category);
          }
      } else if (hasCategory) {
          products = productRepository.findByCategory(category);
      } else if (hasSort) {
          if (sort.equals("asc")) {
              products = productRepository.findAllByOrderByPriceAsc();
          } else if (sort.equals("desc")) {
              products = productRepository.findAllByOrderByPriceDesc();
          } else {
              products = productRepository.findAll();
          }
      } else {
          products = productRepository.findAll();
      }

      model.addAttribute("products", products);
      model.addAttribute("selectedCategory", category);
      model.addAttribute("selectedSort", sort);
      return "product-list";
  }

  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    model.addAttribute("product", productRepository.findById(id).orElseThrow());
    model.addAttribute("reviews", reviewRepository.findByProductId(id));
    model.addAttribute("newReview", new Review());
    return "product-detail";
  }
  
  

  @PostMapping("/{id}/review")
  public String review(@PathVariable Long id,
                       @RequestParam String comment,
                       @RequestParam int rating,
                       Principal principal) {

      Product product = productRepository.findById(id).orElseThrow();
      User user = userRepository.findByUsername(principal.getName()).orElseThrow();

      Review review = new Review();
      review.setComment(comment);
      review.setRating(rating);
      review.setProduct(product);
      review.setUser(user);
      review.setCreatedAt(LocalDateTime.now());

      reviewRepository.save(review);

      return "redirect:/products/" + id;

}
 }