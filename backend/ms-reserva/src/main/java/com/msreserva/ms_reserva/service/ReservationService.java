// ReservationService.java
package com.msreserva.ms_reserva.service;

import com.msreserva.ms_reserva.model.*;
import com.msreserva.ms_reserva.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.msreserva.ms_reserva.dto.ReservationRequestDto;
import com.msreserva.ms_reserva.dto.ReservationResponseDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;
import com.msreserva.ms_reserva.model.ReservationStatusHistory;

@Service
public class ReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
    private final ReservationRepository reservationRepository;
    private final ReservationStatusRepository statusRepository;
    private final ReservationStatusHistoryRepository historyRepository;
    private final ModelMapper modelMapper;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationStatusRepository statusRepository,
                              ReservationStatusHistoryRepository historyRepository,
                              ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.statusRepository = statusRepository;
        this.historyRepository = historyRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Reservation changeStatus(Long reservationId, ReservationStatusChangeDTO dto) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        ReservationStatusEntity newStatus = statusRepository.findByCode(dto.getNewStatusCode())
                .orElseThrow(() -> new RuntimeException("Status not found"));
        ReservationStatusEntity oldStatus = (ReservationStatusEntity) reservation.getStatus();
        reservation.setStatus(newStatus);
        if ("CHECK_IN".equals(newStatus.getCode())) {
            reservation.setCheckInDone(true);
        }
        Reservation updatedReservation = reservationRepository.save(reservation);
        createStatusHistory(updatedReservation, oldStatus, newStatus);
        return updatedReservation;
    }

    private void createStatusHistory(Reservation reservation, ReservationStatusEntity oldStatus, ReservationStatusEntity newStatus) {
        ReservationStatusHistory history = new ReservationStatusHistory();
        history.setReservation(reservation);
        history.setChangeDate(LocalDateTime.now());
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        historyRepository.save(history);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<ReservationStatusHistory> getStatusHistory(Long reservationId) {
        return historyRepository.findByReservationIdOrderByChangeDateDesc(reservationId);
    }

    public ReservationResponseDto createReservation(ReservationRequestDto dto) {
        ReservationStatusEntity status = statusRepository.findByCode("CRIADA")
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));
        Reservation reservation = new Reservation();
        reservation.setClientId(dto.getClientId());
        reservation.setFlightCode(dto.getFlightCode());
        reservation.setReservationDate(java.time.LocalDateTime.now());
        reservation.setStatus(status);
        reservation.setCheckInDone(false);
        Reservation saved = reservationRepository.save(reservation);
        logger.info("Reserva criada: {}", saved.getId());
        return toResponseDto(saved);
    }

    public List<ReservationResponseDto> listByClient(Long clientId) {
        return reservationRepository.findByClientId(clientId).stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    public ReservationResponseDto findById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        return toResponseDto(reservation);
    }

    public ReservationResponseDto cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        ReservationStatusEntity status = statusRepository.findByCode("CANCELADA").orElseThrow(() -> new RuntimeException("Status não encontrado"));
        reservation.setStatus(status);
        reservation = reservationRepository.save(reservation);
        logger.info("Reserva cancelada: {}", id);
        return toResponseDto(reservation);
    }

    public ReservationResponseDto checkInReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        ReservationStatusEntity status = statusRepository.findByCode("CHECK_IN").orElseThrow(() -> new RuntimeException("Status não encontrado"));
        reservation.setStatus(status);
        reservation.setCheckInDone(true);
        reservation = reservationRepository.save(reservation);
        logger.info("Check-in realizado: {}", id);
        return toResponseDto(reservation);
    }

    public void handleFlightStatusEvent(String event) {
        // TODO: Parse event, atualizar reservas conforme status do voo (CANCELADO, REALIZADO, etc.)
        logger.info("Processando evento de status de voo: {}", event);
    }

    private ReservationResponseDto toResponseDto(Reservation reservation) {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(reservation.getId());
        dto.setClientId(reservation.getClientId());
        dto.setFlightCode(reservation.getFlightCode());
        dto.setReservationDate(reservation.getReservationDate().toString());
        dto.setStatus(reservation.getStatus().getCode());
        dto.setCheckInDone(reservation.isCheckInDone());
        return dto;
    }
}