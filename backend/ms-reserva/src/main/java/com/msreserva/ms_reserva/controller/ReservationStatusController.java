package com.msreserva.ms_reserva.controller;

import com.msreserva.ms_reserva.model.ReservationStatusEntity;
import com.msreserva.ms_reserva.service.ReservationStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation-status")
public class ReservationStatusController {

    private final ReservationStatusService service;

    public ReservationStatusController(ReservationStatusService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationStatusEntity> getAllStatuses() {
        return service.getAllStatuses();
    }
}
