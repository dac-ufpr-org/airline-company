// ReservationStatusService.java
package com.msreserva.ms_reserva.service;

import com.msreserva.ms_reserva.model.ReservationStatusEntity;
import com.msreserva.ms_reserva.repository.ReservationStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationStatusService {

    private final ReservationStatusRepository repository;

    public ReservationStatusService(ReservationStatusRepository repository) {
        this.repository = repository;
    }

    public List<ReservationStatusEntity> getAllStatuses() {
        return repository.findAll();
    }
}