package com.msvoo.ms_voo.dto;

import java.math.BigDecimal;

/**
 * DTO para resposta de voo.
 */
public class FlightResponseDto {
    private Long id;
    private String code;
    private String originCodigo;
    private String originNome;
    private String destinationCodigo;
    private String destinationNome;
    private String departureTime;
    private BigDecimal price;
    private int seatCount;
    private int occupiedSeats;
    private String status;
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getOriginCodigo() { return originCodigo; }
    public void setOriginCodigo(String originCodigo) { this.originCodigo = originCodigo; }
    public String getOriginNome() { return originNome; }
    public void setOriginNome(String originNome) { this.originNome = originNome; }
    public String getDestinationCodigo() { return destinationCodigo; }
    public void setDestinationCodigo(String destinationCodigo) { this.destinationCodigo = destinationCodigo; }
    public String getDestinationNome() { return destinationNome; }
    public void setDestinationNome(String destinationNome) { this.destinationNome = destinationNome; }
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getSeatCount() { return seatCount; }
    public void setSeatCount(int seatCount) { this.seatCount = seatCount; }
    public int getOccupiedSeats() { return occupiedSeats; }
    public void setOccupiedSeats(int occupiedSeats) { this.occupiedSeats = occupiedSeats; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 