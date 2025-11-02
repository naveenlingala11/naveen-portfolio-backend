package com.naveen.portfolio.service;

import com.naveen.portfolio.model.Like;
import com.naveen.portfolio.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    private final LikeRepository repo;
    private final NotificationService notificationService;

    public LikeService(LikeRepository repo, NotificationService notificationService) {
        this.repo = repo;
        this.notificationService = notificationService;
    }

    public long getCount() {
        return repo.count();
    }

    @Transactional
    public long toggleLike(String userIdentifier) {
        return repo.findByUserIdentifier(userIdentifier)
                .map(existing -> {
                    repo.delete(existing);
                    notificationService.create("👎 A user unliked a blog", "BLOG_UNLIKED");
                    return repo.count();
                })
                .orElseGet(() -> {
                    repo.save(new Like(userIdentifier));
                    notificationService.create("👍 A user liked a blog", "BLOG_LIKED");
                    return repo.count();
                });
    }

    public boolean hasUserLiked(String userIdentifier) {
        return repo.findByUserIdentifier(userIdentifier).isPresent();
    }
}
