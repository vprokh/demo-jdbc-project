package com.example.hibernate.model;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Embeddable
public class Audit {
    @CreationTimestamp
    private LocalDateTime now;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
