package com.bookingservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document("bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    private String id;

    private String pnr;                        // Auto-generated in service

    private String tripType;                   // ONE_WAY / ROUND_TRIP

    private String flightIdOnward;
    private String flightIdReturn;             // Null for one-way

    private List<String> seatNumbersOnward;
    private List<String> seatNumbersReturn;    // Null for one-way

    private String userName;
    private String email;

    private List<Passenger> passengers;
    private boolean meal;

    private LocalDate journeyDate;             // onward journey
    private LocalDate returnDate;              // round trip only

    private LocalDateTime bookingDateTime;

    private String status;                     // BOOKED / CANCELLED
}
