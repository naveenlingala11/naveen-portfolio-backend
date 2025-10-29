package com.naveen.portfolio.controller;

import com.naveen.portfolio.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "http://localhost:4200")
public class LikeController {
    private final LikeService service;

    public LikeController(LikeService service) {
        this.service = service;
    }

    @GetMapping
    public long getCount() {
        return service.getCount();
    }

    @GetMapping("/user/{id}")
    public boolean hasLiked(@PathVariable String id) {
        return service.hasUserLiked(id);
    }

    @PostMapping("/toggle/{id}")
    public long toggleLike(@PathVariable String id) {
        return service.toggleLike(id);
    }
}
