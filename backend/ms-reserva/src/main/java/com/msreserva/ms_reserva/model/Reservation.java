package com.msreserva.ms_reserva.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.List;

import com.msreserva.ms_reserva.model.ReservationStatusHistory;

@Entity
@Table(name = "reservas")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    
    @Column(name = "flight_code", nullable = false)
    private String flightCode;
    
    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;
    
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ReservationStatusEntity status;
    
    @Column(name = "check_in_done")
    private boolean checkInDone;
    
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationStatusHistory> statusHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatusEntity getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusEntity status) {
        this.status = status;
    }

    public boolean isCheckInDone() {
        return checkInDone;
    }

    public void setCheckInDone(boolean checkInDone) {
        this.checkInDone = checkInDone;
    }

    public List<ReservationStatusHistory> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<ReservationStatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    // Construtor padrão
    public Reservation() {
    }

    // Construtor com parâmetros
    public Reservation(Long clientId, String flightCode, LocalDateTime reservationDate, ReservationStatusEntity status) {
        this.clientId = clientId;
        this.flightCode = flightCode;
        this.reservationDate = reservationDate;
        this.status = status;
        this.checkInDone = false;
    }
}