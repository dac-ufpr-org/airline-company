package com.mscliente.ms_cliente.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String cep;
    private String ruaNumero;
    private String complemento;
    private String cidade;
    private String uf;
}