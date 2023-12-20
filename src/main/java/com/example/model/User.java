package com.example.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String passoword;
    private Role role;
    private Address address;
}
