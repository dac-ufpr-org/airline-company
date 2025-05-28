package com.mscliente.ms_cliente.exceptions.specific;

public class ClienteNaoExisteException extends RuntimeException {
    public ClienteNaoExisteException(String mensagem) {
        super(mensagem);
    }
}
