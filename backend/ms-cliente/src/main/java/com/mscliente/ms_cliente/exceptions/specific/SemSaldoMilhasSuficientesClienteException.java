package br.com.cliente.ms_cliente.exceptions.specific;

public class SemSaldoMilhasSuficientesClienteException extends Exception{
    public SemSaldoMilhasSuficientesClienteException(String mensagem){
        super(mensagem);
    }
}