package com.msvoo.ms_voo.model;

import java.io.Serializable;

import com.msvoos.ms_voos.enums.TipoEstadoVoo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="fligth_state")
public class EstadoVoo implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name="type", insertable = false, updatable = false, nullable = false, unique = true)
    private TipoEstadoVoo tipoEstadoVoo = TipoEstadoVoo.CONFIRMADO;
}