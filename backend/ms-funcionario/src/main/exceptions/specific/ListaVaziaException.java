package br.com.msfuncionario.exeptions;

public class ListaVaziaException extends Exception {
    public ListaVaziaException (String mensagem) {
        super(mensagem);
    }
}