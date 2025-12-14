package com.bookingservice.request;

import com.bookingservice.model.Passenger;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    private String tripType;                 // "ONE_WAY" / "ROUND_TRIP"

    private String flightIdOnward;
    private String flightIdReturn;          // null / empty for one-way

    private List<String> seatNumbersOnward;
    private List<String> seatNumbersReturn; // null / empty for one-way

    private String userName;
    private String email;

    private List<Passenger> passengers;
    private boolean meal;

    private LocalDate journeyDate;
    private LocalDate returnDate;           // only round trip
}
