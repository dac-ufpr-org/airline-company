// ReservationController.java
package com.msreserva.ms_reserva.controller;

import com.msreserva.ms_reserva.model.Reservation;
import com.msreserva.ms_reserva.service.ReservationService;
import com.msreserva.ms_reserva.service.ReservationStatusChangeDTO;
import com.msreserva.ms_reserva.dto.ReservationRequestDto;
import com.msreserva.ms_reserva.dto.ReservationResponseDto;
import com.msreserva.ms_reserva.model.ReservationStatusHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationResponseDto create(@RequestBody ReservationRequestDto dto) {
        logger.info("[CRIAR RESERVA] Recebida requisição para criar reserva: {}", dto);
        return reservationService.createReservation(dto);
    }

    @GetMapping("/client/{clientId}")
    public List<ReservationResponseDto> listByClient(@PathVariable Long clientId) {
        logger.info("[LISTAR RESERVAS] Listando reservas do cliente: {}", clientId);
        return reservationService.listByClient(clientId);
    }

    @GetMapping("/{id}")
    public ReservationResponseDto findById(@PathVariable Long id) {
        logger.info("[CONSULTAR RESERVA] Consultando reserva ID: {}", id);
        return reservationService.findById(id);
    }

    @PutMapping("/{id}/cancel")
    public ReservationResponseDto cancel(@PathVariable Long id) {
        logger.info("[CANCELAR RESERVA] Cancelando reserva ID: {}", id);
        return reservationService.cancelReservation(id);
    }

    @PutMapping("/{id}/check-in")
    public ReservationResponseDto checkIn(@PathVariable Long id) {
        logger.info("[CHECK-IN] Realizando check-in da reserva ID: {}", id);
        return reservationService.checkInReservation(id);
    }

    @PostMapping("/flight-status-event")
    public void receiveFlightStatusEvent(@RequestBody String event) {
        logger.info("[EVENTO VOO] Recebido evento de status de voo: {}", event);
        reservationService.handleFlightStatusEvent(event);
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
