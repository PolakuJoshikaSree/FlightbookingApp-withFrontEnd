package com.bookingservice.service.impl;

import com.bookingservice.client.FlightClient;
import com.bookingservice.kafka.BookingEventProducer;
import com.bookingservice.model.Booking;
import com.bookingservice.repository.BookingRepository;
import com.bookingservice.request.BookingRequest;
import com.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repo;
    private final FlightClient flightClient;
    private final BookingEventProducer eventProducer; // <-- KAFKA PRODUCER

    @Override
    public ResponseEntity<Booking> bookFlight(BookingRequest req) {

        // Reserve onward seats
        boolean onward = flightClient.reserveSeats(req.getFlightIdOnward(), req.getSeatNumbersOnward().size());

        // Reserve return seats only for ROUND_TRIP
        boolean returning = req.getTripType().equalsIgnoreCase("ROUND_TRIP")
                ? flightClient.reserveSeats(req.getFlightIdReturn(), req.getSeatNumbersReturn().size())
                : true;

        if (!onward || !returning)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        // Build & store booking
        Booking booking = Booking.builder()
                .pnr(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .tripType(req.getTripType())
                .flightIdOnward(req.getFlightIdOnward())
                .flightIdReturn(req.getTripType().equalsIgnoreCase("ROUND_TRIP") ? req.getFlightIdReturn() : null)
                .seatNumbersOnward(req.getSeatNumbersOnward())
                .seatNumbersReturn(req.getTripType().equalsIgnoreCase("ROUND_TRIP") ? req.getSeatNumbersReturn() : null)
                .userName(req.getUserName())
                .email(req.getEmail())
                .passengers(req.getPassengers())
                .meal(req.isMeal())
                .journeyDate(req.getJourneyDate())
                .returnDate(req.getReturnDate())
                .bookingDateTime(LocalDateTime.now())
                .status("BOOKED")
                .build();

        repo.save(booking);

        //  SEND KAFKA NOTIFICATION
        eventProducer.sendBookingMessage("Booking Successful - PNR: " + booking.getPnr());

        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @Override
    public ResponseEntity<Booking> getByPnr(String pnr) {
        return repo.findByPnr(pnr)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Override
    public List<Booking> getHistoryByEmail(String email) {
        return repo.findByEmail(email);
    }    @Override
    public ResponseEntity<String> cancel(String pnr) {

        return repo.findByPnr(pnr).map(b -> {

            // allow cancellation only before 24H from journey
            if (!LocalDateTime.now().isBefore(b.getJourneyDate().atStartOfDay().minusHours(24)))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(" Cancellation allowed only before 24 hours of journey");

            // release seats
            flightClient.releaseSeats(b.getFlightIdOnward(), b.getSeatNumbersOnward().size());

            if ("ROUND_TRIP".equalsIgnoreCase(b.getTripType()))
                flightClient.releaseSeats(b.getFlightIdReturn(), b.getSeatNumbersReturn().size());

            repo.delete(b);

            //  SEND KAFKA EVENT
            eventProducer.sendCancellationMessage("Booking Cancelled - PNR: " + pnr);

            return ResponseEntity.ok("Booking Cancelled Successfully");

        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("PNR Not Found"));
    }
}
