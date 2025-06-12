// ReservationController.java
package com.msreserva.ms_reserva.controller;

import com.msreserva.ms_reserva.model.Reservation;
import com.msreserva.ms_reserva.service.ReservationService;
import com.msreserva.ms_reserva.service.ReservationStatusChangeDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationDTO dto) {
        return ResponseEntity.ok(reservationService.createReservation(dto));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<Reservation> changeStatus(
            @PathVariable Long id,
            @RequestBody ReservationStatusChangeDTO dto) {
        return ResponseEntity.ok(reservationService.changeStatus(id, dto));
    }
    @GetMapping("/{id}/history")
    public ResponseEntity<List<ReservationStatusHistory>> getHistory(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getStatusHistory(id));
    }
}   // }
