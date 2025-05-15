package com.msvoo.ms_voo.repository;

import com.msvoo.ms_voo.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {
    //métodos personalizados
}

