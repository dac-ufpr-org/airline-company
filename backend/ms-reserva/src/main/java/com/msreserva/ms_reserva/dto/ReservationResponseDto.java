package com.msreserva.ms_reserva.dto;

public class ReservationResponseDto {
    private Long id;
    private Long clientId;
    private String flightCode;
    private String reservationDate;
    private String status;
    private boolean checkInDone;
    // ... outros campos necessários
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getFlightCode() { return flightCode; }
    public void setFlightCode(String flightCode) { this.flightCode = flightCode; }
    public String getReservationDate() { return reservationDate; }
    public void setReservationDate(String reservationDate) { this.reservationDate = reservationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isCheckInDone() { return checkInDone; }
    public void setCheckInDone(boolean checkInDone) { this.checkInDone = checkInDone; }
    // ... outros getters e setters
} 