package com.msvoo.ms_voo.repository;

import com.msvoo.ms_voo.model.EstadoVoo;
import com.msvoo.ms_voo.enums.TipoEstadoVoo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoVooRepository extends JpaRepository<EstadoVoo, Long> {
    EstadoVoo findByTipoEstadoVoo(TipoEstadoVoo tipoEstadoVoo);
} 