package com.forme.nbnb.entity;

import com.forme.nbnb.entity.user.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Home {
    @Id
    @GeneratedValue
    private long id;
    private String address;
    //TODO : transform address to entity
    private String city;

    private String description;
    private Float pricePerNight;
    private boolean availability;
    private boolean archived;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "home")
    private List<Booking> booking;
}
