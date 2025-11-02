package com.naveen.portfolio.service;

import com.naveen.portfolio.model.Blog;
import com.naveen.portfolio.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public Blog createBlog(Blog blog) {
        Blog saved = blogRepository.save(blog);
        notificationService.create("🆕 New blog post added: " + saved.getTitle(), "BLOG_CREATED");
        return saved;
    }

    public Blog updateBlog(Long id, Blog updatedBlog) {
        return blogRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedBlog.getTitle());
                    existing.setContent(updatedBlog.getContent());
                    existing.setImageUrl(updatedBlog.getImageUrl());
                    existing.setCategory(updatedBlog.getCategory());
                    existing.setAuthorName(updatedBlog.getAuthorName());
                    existing.setShortDescription(updatedBlog.getShortDescription());
                    existing.setTags(updatedBlog.getTags());
                    existing.setFeatured(updatedBlog.isFeatured());
                    Blog saved = blogRepository.save(existing);
                    notificationService.create("✏️ Blog updated: " + saved.getTitle(), "BLOG_UPDATED");
                    return saved;
                })
                .orElse(null);
    }

    public void deleteBlog(Long id) {
        Blog blog = getBlogById(id);
        if (blog != null) {
            blogRepository.deleteById(id);
            notificationService.create("🗑️ Blog deleted: " + blog.getTitle(), "BLOG_DELETED");
        }
    }

    public Blog likeBlog(Long id) {
        Blog blog = getBlogById(id);
        if (blog != null) {
            blog.setLikes(blog.getLikes() + 1);
            Blog saved = blogRepository.save(blog);
            notificationService.create("❤️ Blog liked: " + saved.getTitle(), "BLOG_LIKED");
            return saved;
        }
        return null;
    }

    public Blog addComment(Long id, String comment) {
        Blog blog = getBlogById(id);
        if (blog != null) {
            blog.getComments().add(comment);
            Blog saved = blogRepository.save(blog);
            notificationService.create("💬 New comment added to blog: " + saved.getTitle(), "COMMENT_ADDED");
            return saved;
        }
        return null;
    }

    public Blog deleteComment(Long id, int index) {
        Blog blog = getBlogById(id);
        if (blog != null && index >= 0 && index < blog.getComments().size()) {
            blog.getComments().remove(index);
            Blog saved = blogRepository.save(blog);
            notificationService.create("🧹 Comment deleted from blog: " + saved.getTitle(), "COMMENT_DELETED");
            return saved;
        }
        return null;
    }

    public Blog incrementViews(Long id) {
        Blog blog = getBlogById(id);
        if (blog != null) {
            blog.setViews(blog.getViews() + 1);
            Blog saved = blogRepository.save(blog);
            notificationService.create("👁️ Blog viewed: " + saved.getTitle(), "BLOG_VIEWED");
            return saved;
        }
        return null;
    }

    public List<Blog> getFeaturedBlogs() {
        return blogRepository.findByFeaturedTrue();
    }

    public List<Blog> getBlogsByCategory(String category) {
        return blogRepository.findByCategoryIgnoreCase(category);
    }

    public List<Blog> getBlogsByAuthor(String author) {
        return blogRepository.findByAuthorNameIgnoreCase(author);
    }
}
