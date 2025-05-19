package com.mscliente.ms_cliente.dto;

import lombok.Data;

@Data
public class ClientRequestDto {
    private String cpf;
    private String email;
    private String name;

    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
}