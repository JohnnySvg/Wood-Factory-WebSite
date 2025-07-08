package com.lemn.sitelemn.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemn.sitelemn.entity.Product;
import com.lemn.sitelemn.entity.Review;
import com.lemn.sitelemn.entity.User;
import com.lemn.sitelemn.repository.ProductRepository;
import com.lemn.sitelemn.repository.ReviewRepository;
import com.lemn.sitelemn.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addReview(Long productId, String username, String comment, int rating) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setId(null);
        review.setProduct(product);
        review.setUser(user);
        review.setComment(comment);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}