package com.msvoo.ms_voo.repository;

import com.msvoo.ms_voo.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    
}
