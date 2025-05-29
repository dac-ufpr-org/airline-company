package com.msreserva.ms_reserva.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation_status")
public class ReservationStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sigla;

    @Column(nullable = false)
    private String descricao;

    public ReservationStatusEntity() {}

    public ReservationStatusEntity(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}




