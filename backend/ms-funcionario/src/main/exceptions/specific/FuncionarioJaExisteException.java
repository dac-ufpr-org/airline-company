package br.com.msfuncionario.exceptions;

public class FuncionarioJaExisteException extends Exception {
    public FuncionarioJaExisteException (String mensagem) {
        super(mensagem);
    }
}
