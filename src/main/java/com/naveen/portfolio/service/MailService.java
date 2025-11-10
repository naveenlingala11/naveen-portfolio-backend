package com.naveen.portfolio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class MailService {

    @Value("${formspree.url}")
    private String formspreeUrl;

    @Value("${formspree.to}")
    private String recipientEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendNotificationEmail(String subject, String message) {
        try {
            Map<String, String> body = Map.of(
                    "name", "Naveen Portfolio Notifier",
                    "email", recipientEmail,
                    "message", subject + "\n\n" + message
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(formspreeUrl, entity, String.class);

            System.out.println("✅ Notification email sent: " + subject);
        } catch (Exception e) {
            System.err.println("❌ Failed to send notification email: " + e.getMessage());
        }
    }
}
