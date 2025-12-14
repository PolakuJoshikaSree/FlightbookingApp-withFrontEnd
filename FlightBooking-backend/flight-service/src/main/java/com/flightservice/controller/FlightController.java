package com.flightservice.controller;

import com.flightservice.model.Flight;
import com.flightservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService service;

    @PostMapping("/add")
    public ResponseEntity<?> addFlight(@RequestBody Flight f){
        return ResponseEntity.status(201).body(service.addFlight(f));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> search(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String date){
        return ResponseEntity.ok(service.searchFlight(from,to,date));
    }
    @GetMapping("/summary/{id}")
    public ResponseEntity<?> summary(@PathVariable String id){
        return ResponseEntity.ok(service.getFlightSummary(id));
    }


    @PostMapping("/{id}/reserve")
    public boolean reserve(@PathVariable String id,@RequestParam int seats){
        return service.reserveSeats(id,seats);
    }

    @PostMapping("/{id}/release")
    public boolean release(@PathVariable String id,@RequestParam int seats){
        return service.releaseSeats(id,seats);
    }
}
