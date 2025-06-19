package com.mscontracts.ms_contracts.dto.saga;

import com.mscontracts.ms_contracts.enums.SagaStatus;

public class SagaStateDTO {

    private String correlationId;
    private SagaStatus status;
    private String dadosCliente;
    private String erro;
    private String timestamp;

    public SagaStateDTO() {
    }

    public SagaStateDTO(String correlationId, SagaStatus status, String dadosCliente, String timestamp) {
        this.correlationId = correlationId;
        this.status = status;
        this.dadosCliente = dadosCliente;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public SagaStatus getStatus() {
        return status;
    }

    public void setStatus(SagaStatus status) {
        this.status = status;
    }

    public String getDadosCliente() {
        return dadosCliente;
    }

    public void setDadosCliente(String dadosCliente) {
        this.dadosCliente = dadosCliente;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
} 