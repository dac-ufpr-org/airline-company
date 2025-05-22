package com.mscliente.ms_cliente.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String cep;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String neighborhood;
}
