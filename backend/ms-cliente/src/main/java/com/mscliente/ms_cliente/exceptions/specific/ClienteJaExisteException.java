package br.com.cliente.ms_cliente.exceptions.specific;

public class OutroClienteDadosJaExistenteException extends Exception{
    public OutroClienteDadosJaExistenteException(String mensagem){
        super(mensagem);
    }
}