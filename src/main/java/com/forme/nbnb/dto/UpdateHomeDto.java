package com.forme.nbnb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHomeDto {
    private String address;
    private String city;
    private String description;
    private Float pricePerNight;
    private boolean availability;
    private boolean archived;
}
