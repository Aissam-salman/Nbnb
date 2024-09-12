package com.forme.nbnb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Statistics {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
