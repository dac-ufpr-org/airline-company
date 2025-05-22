package com.msreserva.ms_reserva.service;

import com.msreserva.ms_reserva.model.Reservation;
import com.msreserva.ms_reserva.repository.ReservationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RabbitTemplate rabbitTemplate;

    public ReservationService(ReservationRepository reservationRepository, RabbitTemplate rabbitTemplate) {
        this.reservationRepository = reservationRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Reservation save(Reservation reservation) {
        Reservation saved = reservationRepository.save(reservation);
        // Publica evento no RabbitMQ para sincronizar base de consulta
        rabbitTemplate.convertAndSend("reserva.exchange", "reserva.nova", saved);
        return saved;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    // Pode adicionar buscarPorId, deletar, etc. conforme seu controller
}
