package com.mscliente.ms_cliente.controller;

import com.mscliente.ms_cliente.dto.*;
import com.mscliente.ms_cliente.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDto> cadastrar(@RequestBody ClientRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.cadastrar(dto));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClientResponseDto> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clientService.buscarPorCpf(cpf));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> listarTodos() {
        return ResponseEntity.ok(clientService.listarTodos());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClientResponseDto> atualizar(@PathVariable String cpf,
            @RequestBody ClientRequestDto dto) {
        return ResponseEntity.ok(clientService.atualizar(cpf, dto));
    }
}
