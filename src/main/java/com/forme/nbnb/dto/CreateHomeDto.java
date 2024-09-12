package com.forme.nbnb.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeDto {

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String description;

    @NotNull
    private Float pricePerNight;

    @NotNull
    private boolean availability;

    @NotNull
    @NotBlank
    private Long ownerId;
}
