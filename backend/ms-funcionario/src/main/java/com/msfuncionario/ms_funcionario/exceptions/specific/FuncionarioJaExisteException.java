package com.msfuncionario.ms_funcionario.exceptions.specific;

public class FuncionarioJaExisteException extends RuntimeException {
    public FuncionarioJaExisteException (String mensagem) {
        super(mensagem);
    }
}
