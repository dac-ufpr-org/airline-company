package com.msreserva.ms_reserva.dto;

public class ReservationDto {
    private Long flightId;
    private Long userId;
    private int seatCount;

    public ReservationDto() {}

    public ReservationDto(Long flightId, Long userId, int seatCount) {
        this.flightId = flightId;
        this.userId = userId;
        this.seatCount = seatCount;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}