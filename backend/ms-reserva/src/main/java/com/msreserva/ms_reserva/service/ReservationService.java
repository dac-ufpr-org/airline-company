// ReservationService.java
package com.msreserva.ms_reserva.service;

import com.msreserva.ms_reserva.model.*;
import com.msreserva.ms_reserva.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.msreserva.ms_reserva.dto.ReservationRequestDto;
import com.msreserva.ms_reserva.dto.ReservationResponseDto;
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

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationStatusRepository statusRepository,
                              ReservationStatusHistoryRepository historyRepository) {
        this.reservationRepository = reservationRepository;
        this.statusRepository = statusRepository;
        this.historyRepository = historyRepository;
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
        logger.info("[CRIAR RESERVA] Iniciando criação de reserva: clientId={}, flightCode={}", dto.getClientId(), dto.getFlightCode());
        
        ReservationStatusEntity status = statusRepository.findByCode("CRIADA")
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));
        
        // Definir reservationDate explicitamente
        LocalDateTime now = LocalDateTime.now();
        logger.info("[CRIAR RESERVA] Definindo reservationDate: {}", now);
        
        // Criar reserva usando construtor
        Reservation reservation = new Reservation(dto.getClientId(), dto.getFlightCode(), now, status);
        logger.info("[CRIAR RESERVA] Reserva criada via construtor: clientId={}, flightCode={}, reservationDate={}, status={}", 
                   reservation.getClientId(), reservation.getFlightCode(), reservation.getReservationDate(), reservation.getStatus().getCode());
        
        try {
            if (reservation.getReservationDate() == null) {
                logger.error("[CRIAR RESERVA] reservationDate está nulo antes do save!");
                throw new RuntimeException("reservationDate está nulo antes do save!");
            }
            logger.info("[CRIAR RESERVA] Salvando reserva com reservationDate={}", reservation.getReservationDate());
            Reservation savedReservation = reservationRepository.save(reservation);
            logger.info("[CRIAR RESERVA] Reserva salva: id={}, reservationDate={}", savedReservation.getId(), savedReservation.getReservationDate());
            
            // Criar histórico de status
            ReservationStatusHistory history = new ReservationStatusHistory();
            history.setReservation(savedReservation);
            history.setNewStatus(status);
            history.setChangeDate(now);
            historyRepository.save(history);
            logger.info("[CRIAR RESERVA] Histórico de status criado");
            
            // Retornar DTO
            ReservationResponseDto responseDto = new ReservationResponseDto();
            responseDto.setId(savedReservation.getId());
            responseDto.setClientId(savedReservation.getClientId());
            responseDto.setFlightCode(savedReservation.getFlightCode());
            responseDto.setReservationDate(savedReservation.getReservationDate().toString());
            responseDto.setStatus(savedReservation.getStatus().getCode());
            responseDto.setCheckInDone(savedReservation.isCheckInDone());
            
            logger.info("[CRIAR RESERVA] DTO criado: {}", responseDto);
            return responseDto;
            
        } catch (Exception e) {
            logger.error("[CRIAR RESERVA] Erro ao salvar reserva: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao criar reserva: " + e.getMessage());
        }
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

    public ReservationResponseDto toResponseDto(Reservation reservation) {
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