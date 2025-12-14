package com.bookingservice.controller;

import com.bookingservice.model.Booking;
import com.bookingservice.request.BookingRequest;
import com.bookingservice.service.BookingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @PostMapping("/book")
    public ResponseEntity<Booking> book(@RequestBody BookingRequest req) {
        return service.bookFlight(req);
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<Booking> get(@PathVariable String pnr) {
        return service.getByPnr(pnr);
    }
    
    @GetMapping("/history/{email}")
    public ResponseEntity<?> history(@PathVariable String email) {
        List<Booking> list = service.getHistoryByEmail(email);

        if(list.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No bookings found!");

        return ResponseEntity.ok(list);
    }



    @DeleteMapping("/cancel/{pnr}")
    public ResponseEntity<String> cancel(@PathVariable String pnr) {
        return service.cancel(pnr);
    }
}

