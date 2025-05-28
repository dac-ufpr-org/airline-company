package com.mscliente.ms_cliente.exceptions.specific;

public class SemSaldoMilhasSuficientesClienteException extends RuntimeException{
    public SemSaldoMilhasSuficientesClienteException(String mensagem){
        super(mensagem);
    }
}