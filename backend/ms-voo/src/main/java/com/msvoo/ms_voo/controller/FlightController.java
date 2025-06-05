package com.msvoo.ms_voo.controller;

import com.msvoo.ms_voo.dto.FlightRequestDto;
import com.msvoo.ms_voo.dto.FlightResponseDto;
import com.msvoo.ms_voo.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponseDto> createFlight(@Valid @RequestBody FlightRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.saveFlight(dto));
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getAllFlights(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        // Validação adicional do token se necessário
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDto> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDto> updateFlight(
            @PathVariable Long id, 
            @RequestBody FlightRequestDto dto) {
        return ResponseEntity.ok(flightService.updateFlight(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}