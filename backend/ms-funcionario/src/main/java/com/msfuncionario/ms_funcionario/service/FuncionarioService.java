package com.msfuncionario.ms_funcionario.service;

import com.msfuncionario.ms_funcionario.exceptions.specific.FuncionarioNaoEncontradoException;
import com.msfuncionario.ms_funcionario.exceptions.specific.FuncionarioBusinessException;
import com.msfuncionario.ms_funcionario.exceptions.specific.FuncionarioJaExisteException;
import com.msfuncionario.ms_funcionario.exceptions.specific.ListaVaziaException;
import com.msfuncionario.ms_funcionario.dto.request.FuncionarioRequestDto;
import com.msfuncionario.ms_funcionario.dto.response.FuncionarioResponseDto;
import com.msfuncionario.ms_funcionario.dto.request.UsuarioRequestCadastrarDto;
import com.msfuncionario.ms_funcionario.dto.request.UsuarioRequestAtualizarDto;
import com.msfuncionario.ms_funcionario.model.Funcionario;
import com.msfuncionario.ms_funcionario.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<FuncionarioResponseDto> listar() {
        Optional<List<Funcionario>> listaFuncionarioBD = funcionarioRepository.findByAtivo(true);

        if (!listaFuncionarioBD.isPresent() || listaFuncionarioBD.get().isEmpty()) {
            throw new ListaVaziaException("Lista de funcionários ativos está vazia!");
        }

        return listaFuncionarioBD.get().stream()
                .map(funcionario -> modelMapper.map(funcionario, FuncionarioResponseDto.class))
                .collect(Collectors.toList());
    }

    public FuncionarioResponseDto buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado com ID: " + id));

        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }

    public FuncionarioResponseDto buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new FuncionarioBusinessException("Email é obrigatório.");
        }

        Funcionario funcionario = funcionarioRepository.findByEmailAndAtivo(email, true)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário ativo não encontrado para o email: " + email));

        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }

    public UsuarioRequestCadastrarDto cadastrar(FuncionarioRequestDto dto) {
        List<Funcionario> funcionariosExistentes = funcionarioRepository
                .findByCpfOrEmail(dto.getCpf(), dto.getEmail())
                .orElse(Collections.emptyList());

        boolean cpfExists = funcionariosExistentes.stream()
                .anyMatch(f -> f.getCpf().equals(dto.getCpf()) && Boolean.TRUE.equals(f.getAtivo()));

        boolean emailExists = funcionariosExistentes.stream()
                .anyMatch(f -> f.getEmail().equals(dto.getEmail()) && Boolean.TRUE.equals(f.getAtivo()));

        if (cpfExists && emailExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com CPF e email já existentes.");
        } else if (cpfExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com CPF já existente.");
        } else if (emailExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com email já existente.");
        }

        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);

        Optional<Funcionario> funcionarioInativo = funcionariosExistentes.stream()
                .filter(f -> Boolean.FALSE.equals(f.getAtivo()))
                .findFirst();

        funcionarioInativo.ifPresent(f -> funcionario.setId(f.getId()));

        funcionario.setAtivo(true);
        Funcionario funcionarioCriado = funcionarioRepository.save(funcionario);

        UsuarioRequestCadastrarDto usuarioDto = modelMapper.map(funcionarioCriado, UsuarioRequestCadastrarDto.class);
        usuarioDto.setSenha(dto.getSenha());

        return usuarioDto;
    }

    public UsuarioRequestAtualizarDto atualizar(FuncionarioRequestDto dto) {
        Optional<Funcionario> funcionarioBD = funcionarioRepository.findById(dto.getId());
        if (!funcionarioBD.isPresent()) {
            throw new FuncionarioNaoEncontradoException("Funcionário não encontrado.");
        }

        Optional<List<Funcionario>> funcionarioExistente = funcionarioRepository.findByCpfOrEmail(
                dto.getCpf(), dto.getEmail());

        if (funcionarioExistente.isPresent()) {
            List<Funcionario> listaFuncionarioExistente = funcionarioExistente.get();
            boolean duplicado = listaFuncionarioExistente.stream()
                    .anyMatch(funcionario -> !funcionario.getId().equals(dto.getId()));

            if (duplicado) {
                boolean cpfDuplicado = listaFuncionarioExistente.stream()
                        .anyMatch(funcionario -> !funcionario.getId().equals(dto.getId()) &&
                                funcionario.getCpf().equals(dto.getCpf()));
                if (cpfDuplicado) {
                    throw new FuncionarioJaExisteException("Outro funcionário com CPF já existente.");
                } else {
                    throw new FuncionarioJaExisteException("Outro funcionário com email já existente.");
                }
            }
        }

        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        funcionario.setId(dto.getId());

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);

        UsuarioRequestAtualizarDto usuarioDto = modelMapper.map(funcionarioAtualizado, UsuarioRequestAtualizarDto.class);
        usuarioDto.setId(funcionarioAtualizado.getId());
        usuarioDto.setOldEmail(funcionarioBD.get().getEmail());
        usuarioDto.setSenha(dto.getSenha());

        return usuarioDto;
    }

    public FuncionarioResponseDto inativar(String email) {
        if (email == null || email.isBlank()) {
            throw new FuncionarioBusinessException("Email é obrigatório.");
        }

        Funcionario funcionario = funcionarioRepository.findByEmailAndAtivo(email, true)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário ativo não encontrado para o email: " + email));

        funcionario.setAtivo(false);
        Funcionario funcionarioInativado = funcionarioRepository.save(funcionario);

        return modelMapper.map(funcionarioInativado, FuncionarioResponseDto.class);
    }

    public FuncionarioResponseDto ativar(String email) {
        if (email == null || email.isBlank()) {
            throw new FuncionarioBusinessException("Email é obrigatório.");
        }

        Funcionario funcionario = funcionarioRepository.findByEmailAndAtivo(email, false)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário inativo não encontrado para o email: " + email));

        funcionario.setAtivo(true);
        Funcionario funcionarioAtivado = funcionarioRepository.save(funcionario);

        return modelMapper.map(funcionarioAtivado, FuncionarioResponseDto.class);
    }

    public FuncionarioResponseDto remover(String email) {
        if (email == null || email.isBlank()) {
            throw new FuncionarioBusinessException("Email é obrigatório.");
        }

        Funcionario funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário não encontrado para o email: " + email));

        funcionarioRepository.deleteById(funcionario.getId());
        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }
}
