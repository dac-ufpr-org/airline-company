// ReservationService.java
package com.msreserva.ms_reserva.service;

import com.msreserva.ms_reserva.controller.ReservationDTO;
import com.msreserva.ms_reserva.controller.ReservationStatusHistory;
import com.msreserva.ms_reserva.model.*;
import com.msreserva.ms_reserva.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationStatusRepository statusRepository;
    private final ReservationStatusHistoryRepository historyRepository;
    private final AuditoriaReservationService auditoriaService;

    public ReservationService(ReservationRepository reservationRepository,
                            ReservationStatusRepository statusRepository,
                            ReservationStatusHistoryRepository historyRepository,
                            AuditoriaReservationService auditoriaService) {
        this.reservationRepository = reservationRepository;
        this.statusRepository = statusRepository;
        this.historyRepository = historyRepository;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public Reservation createReservation(ReservationDTO dto) {
        ReservationStatusEntity status = statusRepository.findByCode("CRIADA")
                .orElseThrow(() -> new RuntimeException("Status not found"));
        
        Reservation reservation = new Reservation();
        reservation.setClientId(dto.getClientId());
        reservation.setFlightCode(dto.getFlightCode());
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(status);
        reservation.setCheckInDone(false);
        
        Reservation savedReservation = reservationRepository.save(reservation);
        
        createStatusHistory(savedReservation, null, status);
        
        return savedReservation;
    }

    @SuppressWarnings("unlikely-arg-type")
    @Transactional
    public Reservation changeStatus(Long reservationId, ReservationStatusChangeDTO dto) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        ReservationStatusEntity newStatus = statusRepository.findByCode(dto.getNewStatusCode())
                .orElseThrow(() -> new RuntimeException("Status not found"));
        
        ReservationStatusEntity oldStatus = (ReservationStatusEntity) reservation.getStatus();
        reservation.setStatus(newStatus);
        
        if ("CHECK_IN".equals(newStatus.getClass())) {
            reservation.setCheckInDone(true);
        }
        
        Reservation updatedReservation = reservationRepository.save(reservation);
        createStatusHistory(updatedReservation, oldStatus, newStatus);
        
        // Executa auditoria após mudança de status
        auditoriaService.auditReservation(updatedReservation);
        
        return updatedReservation;
    }

    private void createStatusHistory(Reservation reservation, 
                                   ReservationStatusEntity oldStatus, 
                                   ReservationStatusEntity newStatus) {
        ReservationStatusHistory history = new ReservationStatusHistory();
        history.setReservation(reservation);
        history.setChangeDate(LocalDateTime.now());
        history.setOldStatus(oldStatus);
        history.setOldStatus(newStatus);
        historyRepository.save(history);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public List<ReservationStatusHistory> getStatusHistory(Long reservationId) {
        return historyRepository.findByReservationIdOrderByChangeDateDesc(reservationId);
    }
}