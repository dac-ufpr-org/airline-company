package com.msreserva.ms_reserva.repository;

import com.msreserva.ms_reserva.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
}
