package com.msvoo.ms_voo.model;

import com.msvoo.ms_voo.enums.TipoEstadoVoo;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="flight_status")
public class EstadoVoo implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false, unique = true)
    private TipoEstadoVoo tipoEstadoVoo;

    public EstadoVoo() {}
    public EstadoVoo(TipoEstadoVoo tipoEstadoVoo) {
        this.tipoEstadoVoo = tipoEstadoVoo;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoEstadoVoo getTipoEstadoVoo() { return tipoEstadoVoo; }
    public void setTipoEstadoVoo(TipoEstadoVoo tipoEstadoVoo) { this.tipoEstadoVoo = tipoEstadoVoo; }
} 