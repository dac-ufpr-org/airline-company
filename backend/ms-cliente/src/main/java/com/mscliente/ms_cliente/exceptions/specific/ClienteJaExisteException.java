package com.mscliente.ms_cliente.exceptions.specific;

public class ClienteJaExisteException extends RuntimeException {
    public ClienteJaExisteException(String mensagem) {
        super(mensagem);
    }
}
