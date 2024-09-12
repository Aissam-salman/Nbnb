package com.forme.nbnb.service;

import com.forme.nbnb.entity.Statistics;

public interface IStatistics {
    Statistics generateBookingsStatistics();

    void evaluateLocalEconomicImpact();
}
