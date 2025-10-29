package com.naveen.portfolio.service;

import com.naveen.portfolio.model.Like;
import com.naveen.portfolio.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {
    private final LikeRepository repo;

    public LikeService(LikeRepository repo) {
        this.repo = repo;
    }

    public long getCount() {
        return repo.count();
    }

    @Transactional
    public long toggleLike(String userIdentifier) {
        return repo.findByUserIdentifier(userIdentifier)
                .map(existing -> {
                    repo.delete(existing); // 👎 Unlike
                    return repo.count();
                })
                .orElseGet(() -> {
                    repo.save(new Like(userIdentifier)); // 👍 Like
                    return repo.count();
                });
    }

    public boolean hasUserLiked(String userIdentifier) {
        return repo.findByUserIdentifier(userIdentifier).isPresent();
    }
}
