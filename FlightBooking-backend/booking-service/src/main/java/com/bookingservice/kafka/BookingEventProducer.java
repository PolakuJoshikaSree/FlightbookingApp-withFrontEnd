package com.bookingservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingEventProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void sendBookingMessage(String msg) {
        kafkaTemplate.send("booking-events", msg);
    }

    public void sendCancellationMessage(String msg) {
        kafkaTemplate.send("cancellation-events", msg);
    }

}
