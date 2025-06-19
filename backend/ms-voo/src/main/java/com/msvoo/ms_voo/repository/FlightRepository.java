package com.msvoo.ms_voo.repository;

import com.msvoo.ms_voo.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureTimeBetween(LocalDateTime start, LocalDateTime end);
}
