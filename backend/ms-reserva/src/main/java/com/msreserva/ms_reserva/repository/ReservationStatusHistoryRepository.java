// ReservationStatusHistoryRepository.java
package com.msreserva.ms_reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msreserva.ms_reserva.model.ReservationStatusHistory;

import java.util.List;

public interface ReservationStatusHistoryRepository extends JpaRepository<ReservationStatusHistory, Long> {
    List<ReservationStatusHistory> findByReservationIdOrderByChangeDateDesc(Long reservationId);
}