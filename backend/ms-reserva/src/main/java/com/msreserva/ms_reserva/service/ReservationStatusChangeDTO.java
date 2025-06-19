package com.msreserva.ms_reserva.service;

public class ReservationStatusChangeDTO {
    private String newStatusCode;
    private String reason;
    
    // Construtor padrão
    public ReservationStatusChangeDTO() {
    }
    
    // Construtor com parâmetros
    public ReservationStatusChangeDTO(String newStatusCode, String reason) {
        this.newStatusCode = newStatusCode;
        this.reason = reason;
    }
    
    // Getters e Setters
    public String getNewStatusCode() {
        return newStatusCode;
    }
    
    public void setNewStatusCode(String newStatusCode) {
        this.newStatusCode = newStatusCode;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return "ReservationStatusChangeDTO{" +
                "newStatusCode='" + newStatusCode + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
