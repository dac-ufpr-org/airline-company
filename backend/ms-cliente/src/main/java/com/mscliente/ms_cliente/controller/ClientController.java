package com.mscliente.ms_cliente.controller;

import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;
import com.mscontracts.ms_contracts.dto.client.ClientResponseDTO;
import com.mscliente.ms_cliente.service.ClientService;
import com.mscliente.ms_cliente.service.SagaIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador responsável pelos endpoints de gerenciamento de clientes.
 * Gerencia cadastro, consulta, listagem e atualização de clientes.
 * 
 * Endpoints disponíveis:
 * - POST /clientes: Cadastro normal de cliente
 * - POST /clientes/autocadastro: Autocadastro via SAGA (com criação de usuário)
 * - GET /clientes/{cpf}: Busca cliente por CPF
 * - GET /clientes: Lista todos os clientes
 * - PUT /clientes/{cpf}: Atualiza dados do cliente
 */
@Slf4j
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final SagaIntegrationService sagaIntegrationService;

    /**
     * Endpoint para cadastro normal de cliente (sem SAGA).
     * Cria apenas o cliente no banco de dados, sem criar usuário de autenticação.
     * 
     * @param dto Dados do cliente para cadastro
     * @return Cliente criado com dados completos
     */
    @PostMapping
    public ResponseEntity<ClientResponseDTO> cadastrar(@RequestBody ClientRequestDTO dto) {
        log.info("Cadastro normal de cliente: {}", dto.getEmail());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.cadastrar(dto));
    }

    /**
     * Endpoint para autocadastro de cliente via SAGA.
     * Inicia processo de orquestração que cria usuário + cliente + envia email.
     * 
     * Fluxo SAGA:
     * 1. Cliente → ms-sagas (orquestrador)
     * 2. ms-sagas → ms-auth (criar usuário + senha temporária)
     * 3. ms-auth → ms-sagas (confirmação)
     * 4. ms-sagas → ms-cliente (criar cliente)
     * 5. ms-cliente → ms-sagas (confirmação)
     * 6. Email enviado com senha temporária
     * 
     * @param dto Dados do cliente para autocadastro
     * @return Confirmação de início do processo com correlationId
     */
    @PostMapping("/autocadastro")
    public ResponseEntity<?> autocadastrar(@RequestBody ClientRequestDTO dto) {
        log.info("Iniciando autocadastro via SAGA para cliente: {}", dto.getEmail());
        
        try {
            // Inicia SAGA através do serviço de integração
            String correlationId = sagaIntegrationService.iniciarSagaAutocadastro(dto);
            
            log.info("Autocadastro iniciado com correlationId: {}", correlationId);
            
            // Retorna confirmação com correlationId para acompanhamento
            return ResponseEntity.accepted()
                    .header("X-Correlation-ID", correlationId)
                    .body(Map.of(
                        "message", "Cadastro iniciado. Verifique seu e-mail para a senha temporária.",
                        "correlationId", correlationId
                    ));
                    
        } catch (Exception e) {
            log.error("Erro ao processar autocadastro: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao processar cadastro: " + e.getMessage()));
        }
    }

    /**
     * Endpoint para buscar cliente por CPF.
     * 
     * @param cpf CPF do cliente a ser buscado
     * @return Dados completos do cliente
     */
    @GetMapping("/{cpf}")
    public ResponseEntity<ClientResponseDTO> buscarPorCpf(@PathVariable String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);
        return ResponseEntity.ok(clientService.buscarPorCpf(cpf));
    }

    /**
     * Endpoint para listar todos os clientes.
     * Requer autenticação via header Authorization.
     * 
     * @param authHeader Token de autenticação
     * @return Lista de todos os clientes cadastrados
     */
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> listarTodos(
            @RequestHeader("Authorization") String authHeader
    ) {
        log.info("Listando todos os clientes");
        return ResponseEntity.ok(clientService.listarTodos());
    }

    /**
     * Endpoint para atualizar dados do cliente.
     * 
     * @param cpf CPF do cliente a ser atualizado
     * @param dto Novos dados do cliente
     * @return Cliente atualizado
     */
    @PutMapping("/{cpf}")
    public ResponseEntity<ClientResponseDTO> atualizar(@PathVariable String cpf,
            @RequestBody ClientRequestDTO dto) {
        log.info("Atualizando cliente com CPF: {}", cpf);
        return ResponseEntity.ok(clientService.atualizar(cpf, dto));
    }
}
