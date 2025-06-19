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
        
        // Buscar status CONFIRMADO e logar para debug
        EstadoVoo statusConfirmado = estadoVooRepository.findByTipoEstadoVoo(TipoEstadoVoo.CONFIRMADO);
        logger.info("[CRIAR VOO] Status CONFIRMADO encontrado: id={}, tipo={}", statusConfirmado.getId(), statusConfirmado.getTipoEstadoVoo());
        flight.setStatus(statusConfirmado);
        
        flight = flightRepository.save(flight);
        logger.info("Voo criado com sucesso: {} - Status: {} (ID: {})", flight.getCode(), flight.getStatus().getTipoEstadoVoo(), flight.getStatus().getId());
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
        logger.info("[UPDATE STATUS] Iniciando atualização de status: flightId={}, novoStatus={}", flightId, novoStatus);
        
        Flight flight = flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Voo não encontrado"));
        logger.info("[UPDATE STATUS] Voo encontrado: id={}, code={}, statusAtual={}", flight.getId(), flight.getCode(), flight.getStatus().getTipoEstadoVoo());
        
        EstadoVoo estadoAtual = flight.getStatus();
        logger.info("[UPDATE STATUS] Estado atual: id={}, tipo={}", estadoAtual.getId(), estadoAtual.getTipoEstadoVoo());
        
        try {
            // Remover aspas se existirem
            String statusLimpo = novoStatus.replaceAll("\"", "");
            logger.info("[UPDATE STATUS] Status limpo: {}", statusLimpo);
            
            TipoEstadoVoo novoTipo = TipoEstadoVoo.valueOf(statusLimpo);
            logger.info("[UPDATE STATUS] Tipo convertido: {}", novoTipo);
            
            EstadoVoo novoEstado = estadoVooRepository.findByTipoEstadoVoo(novoTipo);
            if (novoEstado == null) {
                throw new RuntimeException("Estado não encontrado: " + novoTipo);
            }
            logger.info("[UPDATE STATUS] Novo estado encontrado: id={}, tipo={}", novoEstado.getId(), novoEstado.getTipoEstadoVoo());
            
            flight.setStatus(novoEstado);
            Flight vooSalvo = flightRepository.save(flight);
            logger.info("[UPDATE STATUS] Voo atualizado com sucesso: id={}, novoStatus={}", vooSalvo.getId(), vooSalvo.getStatus().getTipoEstadoVoo());
            
            return toResponseDto(vooSalvo);
        } catch (IllegalArgumentException e) {
            logger.error("[UPDATE STATUS] Erro ao converter status '{}': {}", novoStatus, e.getMessage());
            throw new RuntimeException("Status inválido: " + novoStatus);
        } catch (Exception e) {
            logger.error("[UPDATE STATUS] Erro inesperado: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao atualizar status: " + e.getMessage());
        }
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