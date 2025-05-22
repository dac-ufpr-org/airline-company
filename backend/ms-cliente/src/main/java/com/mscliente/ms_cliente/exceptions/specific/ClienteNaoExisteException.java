package br.com.cliente.ms_cliente.exceptions.specific;

public class ClienteNaoExisteException extends Exception{
    public ClienteNaoExisteException(String mensagem){
        super(mensagem);
    }
}