package com.naveen.portfolio.service;

import com.naveen.portfolio.model.Review;
import com.naveen.portfolio.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repo;

    public ReviewService(ReviewRepository repo) {
        this.repo = repo;
    }

    public Review saveReview(Review review) {
        return repo.save(review);
    }

    public Page<Review> getAllReviews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return repo.findAll(pageable);
    }

    public Review getLatestReview() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream().findFirst().orElse(null);
    }
}
