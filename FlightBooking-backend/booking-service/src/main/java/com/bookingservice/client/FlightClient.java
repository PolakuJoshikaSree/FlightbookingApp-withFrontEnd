package com.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "flight-service") 
public interface FlightClient {

    @PostMapping("/flights/{id}/reserve")
    Boolean reserveSeats(@PathVariable String id, @RequestParam int seats);

    @PostMapping("/flights/{id}/release")
    Boolean releaseSeats(@PathVariable String id, @RequestParam int seats);
}
