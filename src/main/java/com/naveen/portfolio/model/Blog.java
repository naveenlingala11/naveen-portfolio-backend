package com.naveen.portfolio.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT") // ✅ long URLs or Base64 images
    private String imageUrl;

    private String category; // e.g., “Tech”, “Travel”, “Design”

    private String authorName;

    @Column(columnDefinition = "TEXT") // ✅ summaries can be long too
    private String shortDescription;

    @ElementCollection
    @CollectionTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "int default 0")
    private int likes = 0;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int views = 0;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean featured = false;

    @ElementCollection
    @CollectionTable(name = "blog_comments", joinColumns = @JoinColumn(name = "blog_id"))
    @Column(name = "comment", columnDefinition = "TEXT") // ✅ prevent long comment issues
    private List<String> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "blog_likes_users", joinColumns = @JoinColumn(name = "blog_id"))
    @Column(name = "user_id")
    private List<String> likedBy = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
