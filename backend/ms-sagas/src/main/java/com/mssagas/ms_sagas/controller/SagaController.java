package com.mssagas.ms_sagas.controller;

import com.mscontracts.ms_contracts.dto.saga.SagaStateDTO;
import com.mssagas.ms_sagas.service.CadastroClienteSagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para monitoramento e consulta de estados das SAGAs.
 */
@Slf4j
@RestController
@RequestMapping("/saga")
@RequiredArgsConstructor
public class SagaController {

    private final CadastroClienteSagaService sagaService;

    /**
     * Consulta o estado atual de uma SAGA pelo correlationId.
     */
    @GetMapping("/status/{correlationId}")
    public ResponseEntity<SagaStateDTO> consultarEstadoSaga(@PathVariable String correlationId) {
        log.info("Consultando estado da SAGA: {}", correlationId);
        
        SagaStateDTO estado = sagaService.consultarEstadoSaga(correlationId);
        
        if (estado == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(estado);
    }

    /**
     * Health check para o serviço de SAGA.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("SAGA Service is running");
    }
} 