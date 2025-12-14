package com.flightservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class FlightSummary {
    private String flightId;
    private String from;
    private String to;
    private String date;
    private int totalSeats;
    private int bookedSeats;
    private int availableSeats;
}
