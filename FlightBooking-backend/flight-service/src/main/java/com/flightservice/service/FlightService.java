package com.flightservice.service;

import com.flightservice.model.Flight;
import com.flightservice.model.FlightSummary;

import java.util.List;

public interface FlightService {

    Flight addFlight(Flight f);
    List<Flight> searchFlight(String from, String to, String date);
    boolean reserveSeats(String flightId,int seats);
    boolean releaseSeats(String flightId,int seats);
    FlightSummary getFlightSummary(String flightId);

}
