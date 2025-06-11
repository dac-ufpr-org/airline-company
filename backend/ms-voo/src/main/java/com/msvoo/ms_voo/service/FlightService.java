package com.msvoo.ms_voo.service;

import com.msvoo.ms_voo.entity.Flight;
import com.msvoo.ms_voo.repository.FlightRepository; // Adicione esta importação
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    // Injeção de dependência via construtor (recomendado)
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight); // Implementação real com JPA
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll(); // Retorna todos os voos
    }

    public Flight getFlightById(Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.orElseThrow(() -> 
            new RuntimeException("Voo não encontrado com ID: " + id)
        );
    }

    public Flight updateFlight(Long id, Flight flightDetails) {
        Flight flight = getFlightById(id); // Reusa a lógica existente
        
        // Atualiza os campos necessários
        flight.setCode(flightDetails.getCode());
        flight.setOrigin(flightDetails.getOrigin());
        flight.setDestination(flightDetails.getDestination());
        // ... outros campos
        
        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        Flight flight = getFlightById(id);
        flightRepository.delete(flight);
    }
}