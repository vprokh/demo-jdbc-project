package com.example.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String displayAddress;
    private String city;
    private String postCode;
    private String street;
}
