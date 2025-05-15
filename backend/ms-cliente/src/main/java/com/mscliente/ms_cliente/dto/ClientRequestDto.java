package com.mscliente.ms_cliente.dto;

import lombok.Data;

@Data
public class ClientRequestDto {
    private String cpf;
    private String email;
    private String name;
    private AddressDto address;
}
