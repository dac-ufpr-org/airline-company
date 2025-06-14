package com.mscontracts.ms_contracts.dto.client;

import lombok.Data;

@Data
public class ClientRequestDTO {

    private String cpf;
    private String email;
    private String name;

    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String bairro;
    private String pais;
    private String pontoReferencia;
    private String telefone;
    private String dataNascimento;

}
