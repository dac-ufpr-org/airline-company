package com.msauth.ms_auth.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String login;
    private String password; // Alterado de senha para password
}
