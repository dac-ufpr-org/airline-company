package com.msfuncionario.ms_funcionario..exceptions;

public class FuncionarioJaExisteException extends Exception {
    public FuncionarioJaExisteException (String mensagem) {
        super(mensagem);
    }
}
