package com.mscliente.ms_cliente.controller;

import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;
import com.mscontracts.ms_contracts.dto.client.ClientResponseDTO;
import com.mssagas.ms_sagas.service.CadastroClienteSagaService;
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
    private final CadastroClienteSagaService sagaService;

    // Endpoint para cadastro normal (sem SAGA)
    @PostMapping
    public ResponseEntity<ClientResponseDto> cadastrar(@RequestBody ClientRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.cadastrar(dto));
    }

    // Novo endpoint para autocadastro via SAGA
    @PostMapping("/autocadastro")
    public ResponseEntity<?> autocadastrar(@RequestBody ClientRequestDTO dto) {
        try {
            sagaService.iniciarSagaAutocadastro(dto);
            return ResponseEntity.accepted().body("Cadastro iniciado. Verifique seu e-mail para a senha temporária.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar cadastro: " + e.getMessage());
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClientResponseDto> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clientService.buscarPorCpf(cpf));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> listarTodos(
            @RequestHeader("Authorization") String authHeader
    ) {
        // Validação adicional do token se necessário
        return ResponseEntity.ok(clientService.listarTodos());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<ClientResponseDto> atualizar(@PathVariable String cpf,
            @RequestBody ClientRequestDTO dto) {
        return ResponseEntity.ok(clientService.atualizar(cpf, dto));
    }
}
