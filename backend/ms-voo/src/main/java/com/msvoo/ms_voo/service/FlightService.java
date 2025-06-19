package com.msvoo.ms_voo.service;

import com.msvoo.ms_voo.entity.Flight;
import com.msvoo.ms_voo.repository.FlightRepository; // Adicione esta importação
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.msvoo.ms_voo.dto.FlightRequestDto;
import com.msvoo.ms_voo.dto.FlightResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.msvoo.ms_voo.model.EstadoVoo;
import com.msvoo.ms_voo.enums.TipoEstadoVoo;
import com.msvoo.ms_voo.repository.EstadoVooRepository;
import com.msvoo.ms_voo.entity.Aeroporto;
import com.msvoo.ms_voo.repository.AeroportoRepository;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    private final EstadoVooRepository estadoVooRepository;
    private final AeroportoRepository aeroportoRepository;
    private final RabbitTemplate rabbitTemplate;

    // Injeção de dependência via construtor (recomendado)
    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper, EstadoVooRepository estadoVooRepository, AeroportoRepository aeroportoRepository, RabbitTemplate rabbitTemplate) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
        this.estadoVooRepository = estadoVooRepository;
        this.aeroportoRepository = aeroportoRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public FlightResponseDto saveFlight(FlightRequestDto dto) {
        Flight flight = new Flight();
        flight.setCode(dto.getCode());
        flight.setOrigin(aeroportoRepository.findById(dto.getOriginCodigo()).orElseThrow(() -> new RuntimeException("Aeroporto de origem não encontrado")));
        flight.setDestination(aeroportoRepository.findById(dto.getDestinationCodigo()).orElseThrow(() -> new RuntimeException("Aeroporto de destino não encontrado")));
        flight.setDepartureTime(LocalDateTime.parse(dto.getDepartureTime()));
        flight.setPrice(dto.getPrice());
        flight.setSeatCount(dto.getSeatCount());
        flight.setOccupiedSeats(0);
        flight.setStatus(estadoVooRepository.findByTipoEstadoVoo(TipoEstadoVoo.CONFIRMADO));
        flight = flightRepository.save(flight);
        logger.info("Voo criado com sucesso: {}", flight.getCode());
        return toResponseDto(flight);
    }

    public List<FlightResponseDto> getAllFlights() {
        return flightRepository.findAll().stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    public FlightResponseDto getFlightById(Long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Voo não encontrado"));
        return toResponseDto(flight);
    }

    public FlightResponseDto updateFlight(Long id, FlightRequestDto dto) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Voo não encontrado"));
        flight.setCode(dto.getCode());
        flight.setOrigin(aeroportoRepository.findById(dto.getOriginCodigo()).orElseThrow(() -> new RuntimeException("Aeroporto de origem não encontrado")));
        flight.setDestination(aeroportoRepository.findById(dto.getDestinationCodigo()).orElseThrow(() -> new RuntimeException("Aeroporto de destino não encontrado")));
        flight.setDepartureTime(LocalDateTime.parse(dto.getDepartureTime()));
        flight.setPrice(dto.getPrice());
        flight.setSeatCount(dto.getSeatCount());
        flight = flightRepository.save(flight);
        logger.info("Voo atualizado: {}", flight.getCode());
        return toResponseDto(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
        logger.info("Voo deletado: {}", id);
    }

    public List<FlightResponseDto> getFlightsNext48Hours() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in48h = now.plusHours(48);
        return flightRepository.findByDepartureTimeBetween(now, in48h).stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    public FlightResponseDto updateFlightStatus(Long flightId, String novoStatus) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Voo não encontrado"));
        EstadoVoo estadoAtual = flight.getStatus();
        TipoEstadoVoo novoTipo = TipoEstadoVoo.valueOf(novoStatus);
        if (novoTipo == TipoEstadoVoo.CANCELADO && estadoAtual.getTipoEstadoVoo() != TipoEstadoVoo.CONFIRMADO) {
            throw new RuntimeException("Só é possível cancelar voos com status CONFIRMADO");
        }
        EstadoVoo novoEstado = estadoVooRepository.findByTipoEstadoVoo(novoTipo);
        flight.setStatus(novoEstado);
        flight = flightRepository.save(flight);
        logger.info("Status do voo alterado: {} -> {}", estadoAtual.getTipoEstadoVoo(), novoTipo);
        // Produzir evento RabbitMQ para ms-reserva
        rabbitTemplate.convertAndSend("flight.status.exchange", "flight.status.changed", toStatusEvent(flight));
        return toResponseDto(flight);
    }

    private FlightResponseDto toResponseDto(Flight flight) {
        FlightResponseDto dto = new FlightResponseDto();
        dto.setId(flight.getId());
        dto.setCode(flight.getCode());
        dto.setOriginCodigo(flight.getOrigin().getCodigo());
        dto.setOriginNome(flight.getOrigin().getNome());
        dto.setDestinationCodigo(flight.getDestination().getCodigo());
        dto.setDestinationNome(flight.getDestination().getNome());
        dto.setDepartureTime(flight.getDepartureTime().toString());
        dto.setPrice(flight.getPrice());
        dto.setSeatCount(flight.getSeatCount());
        dto.setOccupiedSeats(flight.getOccupiedSeats());
        dto.setStatus(flight.getStatus().getTipoEstadoVoo().name());
        return dto;
    }

    private Object toStatusEvent(Flight flight) {
        // TODO: Criar e retornar DTO/evento conforme ms-contracts
        return null;
    }
}