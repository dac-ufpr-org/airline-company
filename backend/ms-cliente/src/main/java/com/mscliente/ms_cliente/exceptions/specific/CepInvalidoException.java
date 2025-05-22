package br.com.cliente.ms_cliente.exceptions.specific;

public class CepInvalidoException extends Exception{
    public CepInvalidoException(String mensagem){
        super(mensagem);
    }
}