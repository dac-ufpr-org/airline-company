package com.msreserva.ms_reserva.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_status_history")
public class ReservationStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @ManyToOne
    @JoinColumn(name = "old_status_id")
    private ReservationStatusEntity oldStatus;

    @ManyToOne
    @JoinColumn(name = "new_status_id", nullable = false)
    private ReservationStatusEntity newStatus;

    // Construtor padrão necessário para o Hibernate
    public ReservationStatusHistory() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }
    public LocalDateTime getChangeDate() { return changeDate; }
    public void setChangeDate(LocalDateTime changeDate) { this.changeDate = changeDate; }
    public ReservationStatusEntity getOldStatus() { return oldStatus; }
    public void setOldStatus(ReservationStatusEntity oldStatus) { this.oldStatus = oldStatus; }
    public ReservationStatusEntity getNewStatus() { return newStatus; }
    public void setNewStatus(ReservationStatusEntity newStatus) { this.newStatus = newStatus; }
} 