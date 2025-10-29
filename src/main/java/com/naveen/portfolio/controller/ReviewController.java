package com.naveen.portfolio.controller;

import com.naveen.portfolio.model.Review;
import com.naveen.portfolio.repository.ReviewRepository;
import com.naveen.portfolio.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService service;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService service, ReviewRepository repository) {
        this.service = service;
        this.reviewRepository = repository;
    }

    @PostMapping
    public ResponseEntity<?> addReview(@Valid @RequestBody Review review) {
        Review saved = reviewRepository.save(review);
        return ResponseEntity.ok(saved);
    }

    // ✅ Get all reviews, newest first
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @GetMapping("/api/reviews")
    public Page<Review> getReviews(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size) {
        return reviewRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date")));
    }


    @GetMapping("/latest")
    public ResponseEntity<Review> getLatest() {
        return ResponseEntity.ok(service.getLatestReview());
    }
}
