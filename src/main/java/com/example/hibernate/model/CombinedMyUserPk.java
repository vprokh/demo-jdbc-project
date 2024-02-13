package com.example.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class CombinedMyUserPk implements Serializable {
    private Long id = 1L;
    @Column(name = "first_name", unique = true)
    private String name = "test";
    @Column(name = "last_name", insertable = false, updatable = false)
    private String lastName = "test1";
}
