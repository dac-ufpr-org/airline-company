package com.msvoo.ms_voo.repository;

import com.msvoo.ms_voo.entity.Aeroporto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeroportoRepository extends JpaRepository<Aeroporto, String> {
    //métodos personalizados
}

