package com.flightservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("flights")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Flight {

    @Id
    private String id;
    private String fromCity;
    private String toCity;
    private String date;
    private int totalSeats;
    private int availableSeats;
}
