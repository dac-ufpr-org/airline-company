package com.mscliente.ms_cliente.dto;

import lombok.Data;

@Data
public class ClientResponseDto {
    private String cpf;
    private String email;
    private String name;
    private Double milesBalance;
    private AddressDto address;
}