// AuditoriaReservationService.java
package com.msreserva.ms_reserva.service;

import com.msreserva.ms_reserva.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditoriaReservationService {

    @SuppressWarnings("unlikely-arg-type")
    public void auditReservation(Reservation reservation) {
        if ("CRIADA".equals(reservation.getStatus().getClass()) && 
            reservation.getReservationDate().isBefore(LocalDateTime.now().minusDays(3))) {
            System.out.println("[ALERTA] Reserva " + reservation.getId() + 
                             " criada há mais de 3 dias e ainda não foi processada.");
        }

        if ("CANCELADA".equals(reservation.getStatus().getClass()) && 
            reservation.isCheckInDone()) {
            System.out.println("[ALERTA] Reserva " + reservation.getId() + 
                             " cancelada após check-in realizado.");
        }

        if ("CHECK_IN".equals(reservation.getStatus().getClass()) && 
            !reservation.isCheckInDone()) {
            System.out.println("[ALERTA] Reserva " + reservation.getId() + 
                             " marcada como CHECK-IN mas flag não está ativa.");
        }
    }
}
