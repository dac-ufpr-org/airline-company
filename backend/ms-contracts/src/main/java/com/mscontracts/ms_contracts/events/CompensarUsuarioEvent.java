package com.mscontracts.ms_contracts.events;

public class CompensarUsuarioEvent {

    private String email;
    private String correlationId;
    private String motivo;

    public CompensarUsuarioEvent() {
    }

    public CompensarUsuarioEvent(String email, String correlationId, String motivo) {
        this.email = email;
        this.correlationId = correlationId;
        this.motivo = motivo;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
} 