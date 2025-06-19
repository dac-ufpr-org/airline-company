package com.msfuncionario.ms_funcionario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class FuncionarioExceptionHandler {

    // Captura erros de validação (ex: @CPF, @Pattern, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                erros.put(err.getField(), err.getDefaultMessage())
        );
        System.err.println("[ERRO VALIDACAO] " + erros);
        return ResponseEntity.badRequest().body(erros);
    }

    // Captura qualquer erro genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericError(Exception ex) {
        System.err.println("[ERRO GERAL] " + ex.getClass().getName() + ": " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno.");
    }
}
