package com.naveen.portfolio.controller;

import com.naveen.portfolio.model.Notification;
import com.naveen.portfolio.repository.NotificationRepository;
import com.naveen.portfolio.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {

    private final NotificationRepository notificationRepository;

    private final NotificationService service;

    public NotificationController(NotificationRepository notificationRepository, NotificationService service) {
        this.notificationRepository = notificationRepository;
        this.service = service;
    }

    @GetMapping
    public List<Notification> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return service.create(notification.getMessage(), notification.getType());
    }

    @DeleteMapping
    public void clearAll() {
        service.clearAll();
    }

    @PutMapping("/mark-read")
    public void markAllAsRead() {
        service.markAllAsRead();
    }

    @PutMapping("/mark-unread")
    public void markAllAsUnread() {
        service.markAllAsUnread();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        try {
            service.markAsRead(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace(); // log for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error marking notification as read: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/unread")
    public ResponseEntity<?> markAsUnread(@PathVariable Long id) {
        try {
            service.markAsUnread(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error marking notification as unread: " + e.getMessage());
        }
    }

}
