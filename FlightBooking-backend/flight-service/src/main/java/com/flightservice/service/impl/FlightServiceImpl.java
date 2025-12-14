package com.flightservice.service.impl;

import com.flightservice.model.Flight;
import com.flightservice.model.FlightSummary;
import com.flightservice.repository.FlightRepository;
import com.flightservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository repo;

    @Override
    public Flight addFlight(Flight f) {
        f.setAvailableSeats(f.getTotalSeats());
        return repo.save(f);
    }

    @Override
    public List<Flight> searchFlight(String from, String to, String date) {
        return repo.findByFromCityAndToCityAndDate(from,to,date);
    }

    @Override
    public boolean reserveSeats(String id,int seats){
        Flight f = repo.findById(id).orElse(null);
        if(f == null || f.getAvailableSeats() < seats) return false;

        f.setAvailableSeats(f.getAvailableSeats() - seats);
        repo.save(f);
        return true;
    }

    @Override
    public boolean releaseSeats(String id,int seats){
        Flight f = repo.findById(id).orElse(null);
        if(f == null) return false;

        f.setAvailableSeats(f.getAvailableSeats() + seats);
        repo.save(f);
        return true;
    }
    @Override
    public FlightSummary getFlightSummary(String id){

        Flight f = repo.findById(id)
                .orElseThrow(()-> new RuntimeException("Flight Not Found"));

        int booked = f.getTotalSeats() - f.getAvailableSeats();

        return new FlightSummary(
                f.getId(), f.getFromCity(), f.getToCity(), f.getDate(),
                f.getTotalSeats(), booked, f.getAvailableSeats()
        );
    }

}
