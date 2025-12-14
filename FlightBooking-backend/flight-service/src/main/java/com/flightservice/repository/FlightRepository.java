package com.flightservice.repository;

import com.flightservice.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FlightRepository extends MongoRepository<Flight, String> {

    List<Flight> findByFromCityAndToCityAndDate(String from, String to, String date);
}
