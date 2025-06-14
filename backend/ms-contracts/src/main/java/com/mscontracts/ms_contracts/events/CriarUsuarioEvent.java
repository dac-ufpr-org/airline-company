package com.mscontracts.ms_contracts.events;

public class CriarUsuarioEvent {

    private String email;
    private String senha;
    private String nome;
    private String correlationId;  // Importante para rastrear a saga

    public CriarUsuarioEvent() {
    }

    public CriarUsuarioEvent(String email, String senha, String nome, String correlationId) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.correlationId = correlationId;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
