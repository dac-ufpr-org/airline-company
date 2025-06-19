// ReservationStatusController.java
package com.msreserva.ms_reserva.controller;

import com.msreserva.ms_reserva.model.ReservationStatusEntity;
import com.msreserva.ms_reserva.service.ReservationStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservation-status")
public class ReservationStatusController {

    private final ReservationStatusService statusService;

    public ReservationStatusController(ReservationStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping
    public List<ReservationStatusEntity> findAll() {
        return statusService.getAllStatuses();
    }
}