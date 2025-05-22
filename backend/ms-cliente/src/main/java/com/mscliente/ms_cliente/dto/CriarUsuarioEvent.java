package com.mscliente.ms_cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarUsuarioEvent {

    private String email;
    private String tipo; // "CLIENTE" ou "FUNCIONARIO"
    private String senhaTemporaria;
}
