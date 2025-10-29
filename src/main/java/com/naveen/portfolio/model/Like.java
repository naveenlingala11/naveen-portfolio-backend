package com.naveen.portfolio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_likes", uniqueConstraints = @UniqueConstraint(columnNames = "userIdentifier"))
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userIdentifier; // could be email, IP, or session ID

    public Like() {}
    public Like(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Long getId() { return id; }
    public String getUserIdentifier() { return userIdentifier; }
    public void setUserIdentifier(String userIdentifier) { this.userIdentifier = userIdentifier; }
}
