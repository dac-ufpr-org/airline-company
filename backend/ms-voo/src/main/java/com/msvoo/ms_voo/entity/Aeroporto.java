package com.msvoo.ms_voo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Aeroporto {

    @Id
    @Column(length = 3)
    private String codigo; // Ex: "GRU"

    private String nome;
    private String cidade;
    private String uf;

    public Aeroporto() {
    }

    public Aeroporto(String codigo, String nome, String cidade, String uf) {
        this.codigo = codigo;
        this.nome = nome;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}