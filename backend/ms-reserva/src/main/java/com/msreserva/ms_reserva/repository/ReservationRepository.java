// ReservationRepository.java
package com.msreserva.ms_reserva.repository;

import com.msreserva.ms_reserva.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Long clientId);
    
    @Query("SELECT r FROM Reservation r WHERE r.status.code = :statusCode")
    List<Reservation> findByStatusCode(String statusCode);
}