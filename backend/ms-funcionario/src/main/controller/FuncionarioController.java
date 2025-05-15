package com.msvoo.ms_voo.controller;

import com.msfuncionario.dto.FuncionarioRequestDto;
import com.msfuncionario.dto.FuncionarioResponseDto;
import com.msfuncionario.entity.Funcionario;
import com.msfuncionario.exceptions.FuncionarioJaExisteException;
import com.msfuncionario.exceptions.FuncionarioNaoEncontradoException;
import com.msfuncionario.exceptions.ListaVaziaException;
import com.msfuncionario.service.FuncionarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ms-funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/listar-funcionario")
    public ResponseEntity<List<FuncionarioResponseDto>> listar() throws ListaVaziaException {
        List<FuncionarioResponseDto> funcionarios = funcionarioService.listar();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/listar-funcionario/{id}")
    public ResponseEntity<FuncionarioResponseDto> buscarPorId(@PathVariable Long id) throws FuncionarioNaoEncontradoException {
        FuncionarioResponseDto funcionario = funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/consultar-email/{email}")
    public ResponseEntity<FuncionarioResponseDto> buscarPorEmail(@PathVariable String email) throws FuncionarioNaoEncontradoException {
        FuncionarioResponseDto funcionario = funcionarioService.buscarPorEmail(email);
        return ResponseEntity.ok(funcionario);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody FuncionarioRequestDto dto) throws FuncionarioJaExisteException {
        funcionarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
