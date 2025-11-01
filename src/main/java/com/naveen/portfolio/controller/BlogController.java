package com.naveen.portfolio.controller;

import com.naveen.portfolio.model.Blog;
import com.naveen.portfolio.repository.BlogRepository;
import com.naveen.portfolio.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin(origins = "*")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogRepository blogRepository;

    @GetMapping
    public List<Blog> getAll() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public Blog getById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public Blog create(@RequestBody Blog blog) {
        return blogService.createBlog(blog);
    }

    @PutMapping("/{id}")
    public Blog update(@PathVariable Long id, @RequestBody Blog blog) {
        return blogService.updateBlog(id, blog);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }

    // --- Likes ---
    @PutMapping("/{id}/like")
    public Blog like(@PathVariable Long id) {
        return blogService.likeBlog(id);
    }

    // --- Views ---
    @PutMapping("/{id}/view")
    public ResponseEntity<Blog> incrementView(@PathVariable Long id) {
        try {
            return blogRepository.findById(id)
                    .map(blog -> {
                        blog.setViews(blog.getViews() + 1);
                        blog.setUpdatedAt(LocalDateTime.now());
                        Blog updatedBlog = blogRepository.save(blog);
                        return ResponseEntity.ok(updatedBlog);
                    })
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- Comments ---
    @PostMapping("/{id}/comments")
    public Blog addComment(@PathVariable Long id, @RequestBody String comment) {
        return blogService.addComment(id, comment);
    }

    @DeleteMapping("/{id}/comments/{index}")
    public Blog deleteComment(@PathVariable Long id, @PathVariable int index) {
        return blogService.deleteComment(id, index);
    }

    // --- Featured & Filters ---
    @GetMapping("/featured")
    public List<Blog> featured() {
        return blogService.getFeaturedBlogs();
    }

    @GetMapping("/category/{category}")
    public List<Blog> byCategory(@PathVariable String category) {
        return blogService.getBlogsByCategory(category);
    }

    @GetMapping("/author/{author}")
    public List<Blog> byAuthor(@PathVariable String author) {
        return blogService.getBlogsByAuthor(author);
    }
}
