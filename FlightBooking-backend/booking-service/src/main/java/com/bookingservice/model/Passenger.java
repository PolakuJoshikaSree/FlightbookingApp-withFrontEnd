package com.bookingservice.model;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Passenger {
    private String name;
    private int age;
    private String gender;
}
