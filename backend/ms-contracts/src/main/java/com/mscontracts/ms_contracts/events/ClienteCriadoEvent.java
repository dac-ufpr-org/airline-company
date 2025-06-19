package com.mscontracts.ms_contracts.events;

public class ClienteCriadoEvent {

    private String cpf;
    private String email;
    private String nome;
    private String correlationId;

    public ClienteCriadoEvent() {
    }

    public ClienteCriadoEvent(String cpf, String email, String nome, String correlationId) {
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.correlationId = correlationId;
    }

    // Getters e Setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
} 