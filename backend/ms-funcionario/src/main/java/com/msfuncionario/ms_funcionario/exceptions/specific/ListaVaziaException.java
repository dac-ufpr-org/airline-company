package com.msfuncionario.ms_funcionario.exceptions.specific;

public class ListaVaziaException extends RuntimeException {
    public ListaVaziaException (String mensagem) {
        super(mensagem);
    }
}