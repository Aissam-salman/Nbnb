package com.forme.nbnb.repository;

import com.forme.nbnb.entity.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {
    @Modifying
    @Query(value = "update Home h set h.availability = :status where h.id = :homeId")
    void updateHomeByAvailability(Long homeId, boolean status);
}
