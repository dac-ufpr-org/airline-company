package com.mscliente.ms_cliente.dto;

import lombok.Data;

@Data
public class CriarUsuarioEvent {

    private String email;
    private String tipo;
    private String senhaTemporaria;
}
