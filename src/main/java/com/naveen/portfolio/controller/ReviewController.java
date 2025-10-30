package com.naveen.portfolio.controller;

import com.naveen.portfolio.model.Review;
import com.naveen.portfolio.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@Valid @RequestBody Review review) {
        Review saved = service.saveReview(review);
        return ResponseEntity.ok(saved);
    }

    // ✅ Unified endpoint with pagination support
    @GetMapping
    public ResponseEntity<?> getReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Review> reviews = service.getAllReviews(page, size);
        return ResponseEntity.ok(reviews);
    }

    // ✅ Get latest review
    @GetMapping("/latest")
    public ResponseEntity<Review> getLatest() {
        return ResponseEntity.ok(service.getLatestReview());
    }
}
