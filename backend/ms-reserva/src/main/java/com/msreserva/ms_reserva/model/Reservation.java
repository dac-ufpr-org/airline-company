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

import com.msreserva.ms_reserva.controller.ReservationStatusHistory;

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

    public Object getStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    }

    public LocalDateTime getReservationDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReservationDate'");
    }

    public String getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    public boolean isCheckInDone() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCheckInDone'");
    }

    public void setClientId(Object clientId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setClientId'");
    }

    public void setFlightCode(Object flightCode2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFlightCode'");
    }

    public void setReservationDate(LocalDateTime now) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setReservationDate'");
    }

    public void setStatus(ReservationStatusEntity status2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }

    public void setCheckInDone(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCheckInDone'");
    }
    
    // Getters and Setters
}