package main.java.com.msvoo.ms_voo.service;

import com.msvoo.ms_voo.dto.FlightRequestDto;
import com.msvoo.ms_voo.dto.FlightResponseDto;
import com.msvoo.ms_voo.entity.Flight;
import com.msvoo.ms_voo.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightResponseDto saveFlight(FlightRequestDto dto) {
        Flight flight = mapDtoToEntity(dto);
        Flight savedFlight = flightRepository.save(flight);
        return mapEntityToDto(savedFlight);
    }

    public FlightResponseDto getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return mapEntityToDto(flight);
    }

    public List<FlightResponseDto> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public FlightResponseDto updateFlight(Long id, FlightRequestDto dto) {
        Flight existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));

        updateEntityFromDto(dto, existingFlight);
        Flight updatedFlight = flightRepository.save(existingFlight);
        return mapEntityToDto(updatedFlight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    private Flight mapDtoToEntity(FlightRequestDto dto) {
        return Flight.builder()
                .flightDate(dto.getFlightDate())
                .flightTime(dto.getFlightTime())
                .origin(dto.getOrigin())
                .destination(dto.getDestination())
                .flightNumber(dto.getFlightNumber())
                .departure(dto.getDeparture())
                .arrival(dto.getArrival())
                .status(dto.getStatus())
                .build();
    }

    private FlightResponseDto mapEntityToDto(Flight flight) {
        return FlightResponseDto.builder()
                .id(flight.getId())
                .flightDate(flight.getFlightDate())
                .flightTime(flight.getFlightTime())
                .origin(flight.getOrigin())
                .destination(flight.getDestination())
                .flightNumber(flight.getFlightNumber())
                .departure(flight.getDeparture())
                .arrival(flight.getArrival())
                .status(flight.getStatus())
                .build();
    }

    private void updateEntityFromDto(FlightRequestDto dto, Flight flight) {
        flight.setFlightDate(dto.getFlightDate());
        flight.setFlightTime(dto.getFlightTime());
        flight.setOrigin(dto.getOrigin());
        flight.setDestination(dto.getDestination());
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setDeparture(dto.getDeparture());
        flight.setArrival(dto.getArrival());
        flight.setStatus(dto.getStatus());
    }
}
