package com.naveen.portfolio.service;

import com.naveen.portfolio.model.Notification;
import com.naveen.portfolio.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public List<Notification> getAll() {
        return repo.findAll();
    }

    public Notification create(String message, String type) {
        return repo.save(new Notification(message, type));
    }

    public void clearAll() {
        repo.deleteAll();
    }

    public void markAllAsRead() {
        repo.markAllAsRead();
    }

    public void markAllAsUnread() {
        repo.markAllAsUnread();
    }

    public void markAsRead(Long id) {
        Notification notif = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notif.setRead(true);
        repo.save(notif);
    }

    public void markAsUnread(Long id) {
        Notification notif = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notif.setRead(false);
        repo.save(notif);
    }
}
