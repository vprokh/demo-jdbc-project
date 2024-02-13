package com.example.hibernate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BirthDate {
    private LocalDate date = LocalDate.now();
}
