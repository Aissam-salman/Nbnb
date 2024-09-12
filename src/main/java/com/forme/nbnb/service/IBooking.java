package com.forme.nbnb.service;

import com.forme.nbnb.entity.Booking;

public interface IBooking {
    void confirmBooking(Booking booking);

    void cancelBooking(Booking booking);
}
