package com.naveen.portfolio.repository;

import com.naveen.portfolio.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByCategoryIgnoreCase(String category);
    List<Blog> findByFeaturedTrue();
    List<Blog> findByAuthorNameIgnoreCase(String authorName);
}
