package com.msfuncionario.ms_funcionario..exeptions;

public class ListaVaziaException extends Exception {
    public ListaVaziaException (String mensagem) {
        super(mensagem);
    }
}