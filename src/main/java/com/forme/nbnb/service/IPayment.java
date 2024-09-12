package com.forme.nbnb.service;

import com.forme.nbnb.entity.Payment;

import java.util.List;

public interface IPayment {
    void makePayment();

    List<Payment> getPaymentsHistory();
}
