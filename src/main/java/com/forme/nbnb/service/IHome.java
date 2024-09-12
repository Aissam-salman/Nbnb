package com.forme.nbnb.service;

import com.forme.nbnb.entity.Home;

public interface IHome {
    Home getDetails(Long homeId);

    boolean updateAvailability(Long id, boolean newAvailability);
}
