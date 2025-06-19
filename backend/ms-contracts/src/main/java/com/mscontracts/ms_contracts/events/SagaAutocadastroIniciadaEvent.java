package com.mscontracts.ms_contracts.events;

import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;

public class SagaAutocadastroIniciadaEvent {

    private ClientRequestDTO dadosCliente;
    private String correlationId;
    private String timestamp;

    public SagaAutocadastroIniciadaEvent() {
    }

    public SagaAutocadastroIniciadaEvent(ClientRequestDTO dadosCliente, String correlationId, String timestamp) {
        this.dadosCliente = dadosCliente;
        this.correlationId = correlationId;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public ClientRequestDTO getDadosCliente() {
        return dadosCliente;
    }

    public void setDadosCliente(ClientRequestDTO dadosCliente) {
        this.dadosCliente = dadosCliente;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
} 