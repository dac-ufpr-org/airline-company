package com.mscontracts.ms_contracts.events;

public class UsuarioCriadoEvent {

    private String email;
    private String nome;
    private String correlationId;
    private String senhaTemporaria;

    public UsuarioCriadoEvent() {
    }

    public UsuarioCriadoEvent(String email, String nome, String correlationId, String senhaTemporaria) {
        this.email = email;
        this.nome = nome;
        this.correlationId = correlationId;
        this.senhaTemporaria = senhaTemporaria;
    }

    // Getters e Setters
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

    public String getSenhaTemporaria() {
        return senhaTemporaria;
    }

    public void setSenhaTemporaria(String senhaTemporaria) {
        this.senhaTemporaria = senhaTemporaria;
    }
} 