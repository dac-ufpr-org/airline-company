package com.msvoo.ms_voo.dto;

import java.math.BigDecimal;

/**
 * DTO para requisição de criação/atualização de voo.
 */
public class FlightRequestDto {
    private String code;
    private String originCodigo;
    private String destinationCodigo;
    private String departureTime;
    private BigDecimal price;
    private int seatCount;
    // Getters e Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getOriginCodigo() { return originCodigo; }
    public void setOriginCodigo(String originCodigo) { this.originCodigo = originCodigo; }
    public String getDestinationCodigo() { return destinationCodigo; }
    public void setDestinationCodigo(String destinationCodigo) { this.destinationCodigo = destinationCodigo; }
    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getSeatCount() { return seatCount; }
    public void setSeatCount(int seatCount) { this.seatCount = seatCount; }
} 