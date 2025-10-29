package com.naveen.portfolio.repository;

import com.naveen.portfolio.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

