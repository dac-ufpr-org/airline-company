package com.msvoo.ms_voo.controller;

import com.msvoo.ms_voo.model.EstadoVoo;
import com.msvoo.ms_voo.enums.TipoEstadoVoo;
import com.msvoo.ms_voo.repository.EstadoVooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/flight-status")
public class EstadoVooController {
    private static final Logger logger = LoggerFactory.getLogger(EstadoVooController.class);

    @Autowired
    private EstadoVooRepository estadoVooRepository;

    @GetMapping
    public List<EstadoVoo> listarTodos() {
        logger.info("[LISTAR ESTADOS DE VOO] Listando todos os estados de voo");
        return estadoVooRepository.findAll();
    }

    @PostMapping
    public EstadoVoo criar(@RequestBody TipoEstadoVoo tipo) {
        logger.info("[CRIAR ESTADO DE VOO] Criando estado: {}", tipo);
        return estadoVooRepository.save(new EstadoVoo(tipo));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        logger.info("[DELETAR ESTADO DE VOO] Deletando estado ID: {}", id);
        estadoVooRepository.deleteById(id);
    }
} 