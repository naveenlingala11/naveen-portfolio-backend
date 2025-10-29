package com.naveen.portfolio.repository;

import com.naveen.portfolio.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdentifier(String userIdentifier);
}
