package com.msvoo.ms_voo.controller;

import com.msvoo.ms_voo.entity.Aeroporto;
import com.msvoo.ms_voo.service.AeroportoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aeroportos")
public class AeroportoController {

    @Autowired
    private AeroportoService aeroportoService;

    @PostMapping
    public Aeroporto criar(@RequestBody Aeroporto aeroporto) {
        return aeroportoService.salvar(aeroporto);
    }

    @GetMapping
    public List<Aeroporto> listarTodos() {
        return aeroportoService.listarTodos();
    }
}
