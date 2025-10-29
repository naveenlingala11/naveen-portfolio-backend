package com.naveen.portfolio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class LikeCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long count = 0;

    public LikeCounter() {}

    // getters/setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
