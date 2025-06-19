package com.msreserva.ms_reserva.dto;

public class ReservationRequestDto {
    private Long clientId;
    private String flightCode;
    // ... outros campos necessários
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getFlightCode() { return flightCode; }
    public void setFlightCode(String flightCode) { this.flightCode = flightCode; }
    // ... outros getters e setters
} 