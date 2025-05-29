package com.mscliente.ms_cliente.dto;

import lombok.Data;

@Data
public class ClientResponseDto {
    private String cpf;
    private String email;
    private String name;
    private Double milesBalance;
    private AddressDto address;
    private String phone;
    private String birthDate;
    private String userId; // ID do usuário associado ao cliente
    private String userType; // Tipo de usuário (CLIENTE ou FUNCIONARIO)

    
}