package com.notificationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingEventListener {

    @KafkaListener(topics = "booking-events", groupId = "notification-group")
    public void consumeBooking(String message) {
        System.out.println(" Notification: " + message);
    }

    @KafkaListener(topics = "cancellation-events", groupId = "notification-group")
    public void consumeCancellation(String message) {
        System.out.println(" Notification: " + message);
    }
}
