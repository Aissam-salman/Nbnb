package com.forme.nbnb.entity.user;

import com.forme.nbnb.entity.Booking;
import com.forme.nbnb.entity.Home;

import java.util.List;

public interface ITenant {
    List<Home> searchHousing();

    void reserveHousing(Home home);

    void cancelBooking(Booking booking);
}
