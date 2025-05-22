package com.msauth.ms_auth.dto;

import lombok.Data;

@Data
public class RegisterDTO {

    private String login;
    private String password;  // Alterado de 'senha' para 'password'
    private String tipo;
}
