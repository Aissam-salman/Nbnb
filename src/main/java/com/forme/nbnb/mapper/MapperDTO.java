package com.forme.nbnb.mapper;

import com.forme.nbnb.dto.HomeDto;
import com.forme.nbnb.dto.UserDto;
import com.forme.nbnb.entity.Home;
import com.forme.nbnb.entity.user.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class MapperDTO {

    public static UserDto currentUserToUserDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        if (currentUser == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(currentUser.getId());
        userDto.setEmail(currentUser.getEmail());
        userDto.setFirstname(currentUser.getFirstname());
        userDto.setLastname(currentUser.getLastname());
        userDto.setPicture(currentUser.getPicture());
        userDto.setRole(currentUser.getRole());
        return userDto;
    }

    public static HomeDto homeToHomeDto(Home home) {
        return new HomeDto(
                home.getId(),
                home.getAddress(),
                home.getCity(),
                home.getDescription(),
                home.getPricePerNight(),
                home.isAvailability(),
                home.isArchived(),
                home.getOwner().getId()
        );
    }
}
