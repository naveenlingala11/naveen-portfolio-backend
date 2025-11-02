package com.naveen.portfolio.repository;

import com.naveen.portfolio.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Notification n SET n.read = true")
    void markAllAsRead();

    @Transactional
    @Modifying
    @Query("UPDATE Notification n SET n.read = false")
    void markAllAsUnread();
}
