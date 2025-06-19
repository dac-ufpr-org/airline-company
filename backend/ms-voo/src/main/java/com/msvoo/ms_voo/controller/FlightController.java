package com.msvoo.ms_voo.controller;

import com.msvoo.ms_voo.entity.Flight;
import com.msvoo.ms_voo.service.FlightService;
import com.msvoo.ms_voo.dto.FlightRequestDto;
import com.msvoo.ms_voo.dto.FlightResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    // Criar voo
    @PostMapping
    public FlightResponseDto createFlight(@RequestBody FlightRequestDto dto) {
        logger.info("[CRIAR VOO] Recebida requisição para criar voo: {}", dto);
        return flightService.saveFlight(dto);
    }

    // Listar todos os voos
    @GetMapping
    public List<FlightResponseDto> getAllFlights() {
        logger.info("[LISTAR VOOS] Listando todos os voos");
        return flightService.getAllFlights();
    }

    // Buscar voo por ID
    @GetMapping("/{id}")
    public FlightResponseDto getFlightById(@PathVariable Long id) {
        logger.info("[BUSCAR VOO] Buscando voo por ID: {}", id);
        return flightService.getFlightById(id);
    }

    // Atualizar voo
    @PutMapping("/{id}")
    public FlightResponseDto updateFlight(@PathVariable Long id, @RequestBody FlightRequestDto dto) {
        logger.info("[ATUALIZAR VOO] Atualizando voo ID: {}", id);
        return flightService.updateFlight(id, dto);
    }

    // Deletar voo
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        logger.info("[DELETAR VOO] Deletando voo ID: {}", id);
        flightService.deleteFlight(id);
    }

    // Listar voos nas próximas 48 horas
    @GetMapping("/next-48-hours")
    public List<FlightResponseDto> getFlightsNext48Hours() {
        logger.info("[VOOS 48H] Listando voos nas próximas 48 horas");
        return flightService.getFlightsNext48Hours();
    }

    // Atualizar status do voo
    @PutMapping("/{flightId}/status")
    public FlightResponseDto updateFlightStatus(@PathVariable Long flightId, @RequestBody String novoStatus) {
        logger.info("[ATUALIZAR STATUS] Atualizando status do voo ID: {} para {}", flightId, novoStatus);
        return flightService.updateFlightStatus(flightId, novoStatus);
    }
}