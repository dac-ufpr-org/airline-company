package com.mscliente.ms_cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarUsuarioEvent implements Serializable {

    private String email;
    private String tipo; // "CLIENTE" ou "FUNCIONARIO"
    private String senhaTemporaria;
    
}
