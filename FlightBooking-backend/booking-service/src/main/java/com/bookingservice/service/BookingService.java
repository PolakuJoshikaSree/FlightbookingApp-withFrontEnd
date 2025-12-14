package com.bookingservice.service;

import com.bookingservice.model.Booking;
import com.bookingservice.request.BookingRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {

    ResponseEntity<Booking> bookFlight(BookingRequest req);
    ResponseEntity<Booking> getByPnr(String pnr);
    List<Booking> getHistoryByEmail(String email);
    ResponseEntity<String> cancel(String pnr);

}
