// ReservationStatusRepository.java
package com.msreserva.ms_reserva.repository;

import com.msreserva.ms_reserva.model.ReservationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatusEntity, Long> {
    Optional<ReservationStatusEntity> findByCode(String code);
}