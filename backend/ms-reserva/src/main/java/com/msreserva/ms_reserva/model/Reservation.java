package com.msreserva.ms_reserva.model;

import com.msreserva.ms_reserva.model.ReservationStatusEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {
    
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private String flightCode;

    private LocalDate reservationDate;

    @ManyToOne
    @JoinColumn(name = "status_id") // nome da coluna no banco
    private ReservationStatusEntity status;

    public Reservation() {}

    public Reservation(Long clientId, String flightCode, LocalDate reservationDate, ReservationStatus status) {
        this.clientId = clientId;
        this.flightCode = flightCode;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatusEntity getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusEntity status) {
        this.status = status;
    }
}
    

