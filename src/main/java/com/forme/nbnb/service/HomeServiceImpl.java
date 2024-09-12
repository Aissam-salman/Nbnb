package com.forme.nbnb.service;

import com.forme.nbnb.dto.CreateHomeDto;
import com.forme.nbnb.dto.UpdateHomeDto;
import com.forme.nbnb.entity.Home;
import com.forme.nbnb.entity.user.Owner;
import com.forme.nbnb.repository.HomeRepository;
import com.forme.nbnb.repository.user.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements IHome {

    private final HomeRepository homeRepository;
    private final OwnerRepository ownerRepository;

    public Home create(CreateHomeDto createHomeDto) {
        Owner owner =
                ownerRepository.findById(createHomeDto.getOwnerId()).orElseThrow(() -> new RuntimeException("Owner not found"));

        Home newHome = Home.builder()
                .address(createHomeDto.getAddress())
                .description(createHomeDto.getDescription())
                .pricePerNight(createHomeDto.getPricePerNight())
                .availability(createHomeDto.isAvailability())
                .owner(owner)
                .city(createHomeDto.getCity())
                .archived(false)
                .build();

        return homeRepository.save(newHome);
    }

    public boolean updateAll(Long homeId, UpdateHomeDto updateHomeDto) {
        Home homeUpdated = homeRepository.findById(homeId).orElseThrow(() -> new RuntimeException("Home not found"));

        if (updateHomeDto.getDescription() != null) {
            homeUpdated.setDescription(updateHomeDto.getDescription());
        }
        if (updateHomeDto.getAddress() != null) {
            homeUpdated.setAddress(updateHomeDto.getAddress());
        }
        if (updateHomeDto.getCity() != null) {
            homeUpdated.setCity(updateHomeDto.getCity());
        }
        if (updateHomeDto.getPricePerNight() != null) {
            homeUpdated.setPricePerNight(updateHomeDto.getPricePerNight());
        }
        if (updateHomeDto.isAvailability() != homeUpdated.isAvailability()) {
            homeUpdated.setAvailability(updateHomeDto.isAvailability());
        }
        if (updateHomeDto.isArchived() != homeUpdated.isArchived()) {
            homeUpdated.setArchived(updateHomeDto.isArchived());
        }

        try {
            homeRepository.save(homeUpdated);
            return true;
        } catch (HibernateException e) {
            log.error(e.getMessage());
            return false;
        }

    }

    public List<Home> findAll() {
        return homeRepository.findAll();
    }

    public List<Home> findByAvailability(boolean availability) {
        return List.of();

    }

    public List<Home> findByCity(String city) {
        return List.of();
    }

    public List<Home> findByOwnerId(Long ownerId) {
        return List.of();
    }

    public boolean delete(Long homeId) {
        try {
            homeRepository.deleteById(homeId);
            return true;
        } catch (HibernateException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Home getDetails(Long id) {
        return homeRepository.findById(id).orElseThrow(() -> new RuntimeException("Home not found"));
    }

    @Override
    public boolean updateAvailability(Long id, boolean newAvailability) {
        try {
            homeRepository.updateHomeByAvailability(id, newAvailability);
            return true;
        } catch (HibernateException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
