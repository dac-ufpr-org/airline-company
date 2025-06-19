package com.msvoo.ms_voo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade que representa um voo.
 * Utiliza relacionamentos com Aeroporto (origem/destino) e EstadoVoo (status).
 */
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código do voo
    private String code;

    // Aeroporto de origem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_codigo", referencedColumnName = "codigo")
    private Aeroporto origin;

    // Aeroporto de destino
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_codigo", referencedColumnName = "codigo")
    private Aeroporto destination;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    private BigDecimal price;
    private int seatCount;

    // Poltronas ocupadas
    private int occupiedSeats;

    // Status do voo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_voo_id")
    private com.msvoo.ms_voo.model.EstadoVoo status;

    public Flight() {}

    public Flight(String code, Aeroporto origin, Aeroporto destination, LocalDateTime departureTime,
                  BigDecimal price, int seatCount, com.msvoo.ms_voo.model.EstadoVoo status) {
        this.code = code;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.price = price;
        this.seatCount = seatCount;
        this.status = status;
    }

    // GETTERS E SETTERS 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Aeroporto getOrigin() {
        return origin;
    }

    public void setOrigin(Aeroporto origin) {
        this.origin = origin;
    }

    public Aeroporto getDestination() {
        return destination;
    }

    public void setDestination(Aeroporto destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public com.msvoo.ms_voo.model.EstadoVoo getStatus() {
        return status;
    }

    public void setStatus(com.msvoo.ms_voo.model.EstadoVoo status) {
        this.status = status;
    }
    
}
