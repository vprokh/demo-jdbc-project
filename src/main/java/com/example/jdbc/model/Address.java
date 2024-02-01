package com.example.jdbc.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Address {
    private Long id;
    private String displayAddress;
    private String city;
    private String postCode;
    private String street;
    private LocalDateTime createdAt = LocalDateTime.now();
}
