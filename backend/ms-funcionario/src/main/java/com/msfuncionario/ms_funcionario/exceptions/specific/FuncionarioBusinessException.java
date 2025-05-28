package com.msfuncionario.ms_funcionario.exceptions.specific;

public class FuncionarioBusinessException extends RuntimeException {
    public FuncionarioBusinessException(String mensagem) {
        super(mensagem);
    }
}
