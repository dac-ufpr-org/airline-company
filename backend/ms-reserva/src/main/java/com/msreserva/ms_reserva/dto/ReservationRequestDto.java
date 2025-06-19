package com.msreserva.ms_reserva.dto;

public class ReservationRequestDto {
    private Long clientId;
    private String flightCode;
    // ... outros campos necessários
    
    // Construtor padrão necessário para deserialização JSON
    public ReservationRequestDto() {
    }
    
    // Construtor com parâmetros
    public ReservationRequestDto(Long clientId, String flightCode) {
        this.clientId = clientId;
        this.flightCode = flightCode;
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
    
    @Override
    public String toString() {
        return "ReservationRequestDto{" +
                "clientId=" + clientId +
                ", flightCode='" + flightCode + '\'' +
                '}';
    }
    // ... outros getters e setters
} 