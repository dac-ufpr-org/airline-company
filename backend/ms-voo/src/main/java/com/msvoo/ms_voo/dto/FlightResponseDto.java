package com.msvoo.ms_voo.dto;

import lombok.Data;

@Data
public class FlightResponseDto {
    private Long id;
    private String flightDate;
    private String flightTime;
    private String origin;
    private String destination;
    private String flightNumber;
    private String departure;
    private String arrival;
    private String status;
}