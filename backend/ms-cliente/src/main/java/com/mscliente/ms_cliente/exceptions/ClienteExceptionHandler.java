package com.mscliente.ms_cliente.exceptions;

import com.mscliente.ms_cliente.exceptions.specific.CepInvalidoException;
import com.mscliente.ms_cliente.exceptions.specific.ClienteJaExisteException;
import com.mscliente.ms_cliente.exceptions.specific.ClienteNaoExisteException;
import com.mscliente.ms_cliente.exceptions.specific.SemSaldoMilhasSuficientesClienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClienteExceptionHandler {

    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<String> handleCepInvalido(CepInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteJaExisteException.class)
    public ResponseEntity<String> handleClienteJaExiste(ClienteJaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteNaoExisteException.class)
    public ResponseEntity<String> handleClienteNaoExiste(ClienteNaoExisteException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SemSaldoMilhasSuficientesClienteException.class)
    public ResponseEntity<String> handleSemSaldoMilhas(SemSaldoMilhasSuficientesClienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo de milhas insuficiente: " + ex.getMessage());
    }
}
