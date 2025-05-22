package com.msfuncionario.ms_funcionario.exeptions.specific;

public class ListaVaziaException extends Exception {
    public ListaVaziaException (String mensagem) {
        super(mensagem);
    }
}