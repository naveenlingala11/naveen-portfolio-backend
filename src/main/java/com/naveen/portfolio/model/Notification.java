package com.naveen.portfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Notification {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String message;
    @Setter
    private String type;
    @Setter
    private LocalDateTime timestamp = LocalDateTime.now();
    @Setter
    @Column(nullable = false)
    private boolean read = false;

    public Notification() {}

    public Notification(String message, String type) {
        this.message = message;
        this.type = type;
    }

}
