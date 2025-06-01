package com.msfuncionario.ms_funcionario.exceptions;

import com.msfuncionario.ms_funcionario.exceptions.specific.FuncionarioNaoEncontradoException;
import com.msfuncionario.ms_funcionario.exceptions.specific.FuncionarioJaExisteException;
import com.msfuncionario.ms_funcionario.exceptions.specific.ListaVaziaException;
import com.msfuncionario.ms_funcionario.exceptions.specific.FuncionarioBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VooExceptionHandler {

    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    public ResponseEntity<String> handleFuncionarioNaoEncontrado(FuncionarioNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(FuncionarioJaExisteException.class)
    public ResponseEntity<String> handleFuncionarioJaExiste(FuncionarioJaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ListaVaziaException.class)
    public ResponseEntity<String> handleListaVazia(ListaVaziaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleErroGeral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Erro inesperado. Tente novamente mais tarde.");
    }

    @ExceptionHandler(FuncionarioBusinessException.class)
    public ResponseEntity<String> handleBusinessException(FuncionarioBusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}