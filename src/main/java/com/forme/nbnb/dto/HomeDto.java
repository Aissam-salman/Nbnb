package com.forme.nbnb.dto;

import com.forme.nbnb.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeDto {
    private Long id;
    private String address;
    private String city;
    private String description;
    private Float pricePerNight;
    private boolean availability;
    private boolean archived;
    private Long ownerId;

    private List<Image> imageUrls;

    public List<String> getImageUrls() {
        return imageUrls.stream().map(Image::getUrl).toList();
    }
}
