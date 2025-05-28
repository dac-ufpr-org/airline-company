package com.msfuncionario.ms_funcionario.service;

import com.msfuncionario.ms_funcionario.dto.request.FuncionarioRequestDto;
import com.msfuncionario.ms_funcionario.dto.response.FuncionarioResponseDto;
import com.msfuncionario.ms_funcionario.dto.request.UsuarioRequestCadastrarDto;
import com.msfuncionario.ms_funcionario.dto.request.UsuarioRequestAtualizarDto;
import com.msfuncionario.ms_funcionario.model.Funcionario;
import com.msfuncionario.ms_funcionario.repository.FuncionarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    private void validarDadosObrigatorios(FuncionarioRequestDto dto) {
        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new FuncionarioBusinessException("CPF é obrigatório.");
        }
        if (!dto.getCpf().matches("\\d{11}")) {
            throw new FuncionarioBusinessException("CPF deve conter exatamente 11 dígitos numéricos.");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new FuncionarioBusinessException("Email é obrigatório.");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new FuncionarioBusinessException("Nome é obrigatório.");
        }
        if (dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new FuncionarioBusinessException("Senha é obrigatória.");
        }
        if (dto.getDataAdmissao() != null && dto.getDataAdmissao().isAfter(LocalDate.now())) {
            throw new FuncionarioBusinessException("Data de admissão não pode ser no futuro.");
        }
    }

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

    public FuncionarioResponseDto consultarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new FuncionarioBusinessException("Email é obrigatório.");
        }

        Funcionario funcionario = funcionarioRepository.findByEmailAndAtivo(email, true)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionário ativo não encontrado para o email: " + email));

        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }

    public UsuarioRequestCadastrarDto cadastrar(FuncionarioRequestDto dto) {
        validarDadosObrigatorios(dto);

        List<Funcionario> funcionariosExistentes = funcionarioRepository
                .findByCpfOrEmail(dto.getCpf(), dto.getEmail())
                .orElse(Collections.emptyList());

        boolean cpfExists = false;
        boolean emailExists = false;
        Long idFuncionario = 0L;

        for (Funcionario f : funcionariosExistentes) {
            if (f.isAtivo()) {
                if (f.getCpf().equals(dto.getCpf())) {
                    cpfExists = true;
                }
                if (f.getEmail().equals(dto.getEmail())) {
                    emailExists = true;
                }
            }
        }

        if (cpfExists && emailExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com CPF e email já existentes.");
        } else if (cpfExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com CPF já existente.");
        } else if (emailExists) {
            throw new FuncionarioJaExisteException("Outro funcionário ativo com email já existente.");
        }

        if (funcionariosExistentes.size() == 1) {
            idFuncionario = funcionariosExistentes.get(0).getIdFuncionario();
        } else if (funcionariosExistentes.size() > 1) {
            throw new FuncionarioJaExisteException("Funcionários inativos já existentes com CPF e email.");
        }

        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        funcionario.setIdFuncionario(idFuncionario);

        Funcionario funcionarioCriado = funcionarioRepository.save(funcionario);

        UsuarioRequestCadastrarDto usuarioDto = modelMapper.map(funcionarioCriado, UsuarioRequestCadastrarDto.class);
        usuarioDto.setSenha(dto.getSenha());

        return usuarioDto;
    }

    public UsuarioRequestAtualizarDto atualizar(FuncionarioRequestDto dto) {
        validarDadosObrigatorios(dto);

        Optional<Funcionario> funcionarioBD = funcionarioRepository.findByIdAndAtivo(dto.getIdFuncionario(), true);
        if (!funcionarioBD.isPresent()) {
            throw new FuncionarioNaoEncontradoException("Funcionário ativo não encontrado.");
        }

        Optional<List<Funcionario>> funcionarioExistente = funcionarioRepository.findByCpfOrEmail(
                dto.getCpf(), dto.getEmail());

        if (funcionarioExistente.isPresent()) {
            List<Funcionario> listaFuncionarioExistente = funcionarioExistente.get();
            boolean duplicado = listaFuncionarioExistente.stream()
                    .anyMatch(funcionario -> !funcionario.getIdFuncionario().equals(dto.getIdFuncionario()));

            if (duplicado) {
                boolean cpfDuplicado = listaFuncionarioExistente.stream()
                        .anyMatch(funcionario -> !funcionario.getIdFuncionario().equals(dto.getIdFuncionario()) &&
                                funcionario.getCpf().equals(dto.getCpf()));
                if (cpfDuplicado) {
                    throw new FuncionarioJaExisteException("Outro funcionário com CPF já existente.");
                } else {
                    throw new FuncionarioJaExisteException("Outro funcionário com email já existente.");
                }
            }
        }

        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        funcionario.setIdFuncionario(dto.getIdFuncionario());

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);

        UsuarioRequestAtualizarDto usuarioDto = modelMapper.map(funcionarioAtualizado, UsuarioRequestAtualizarDto.class);
        usuarioDto.setId(funcionarioAtualizado.getIdFuncionario());
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

        funcionarioRepository.deleteById(funcionario.getIdFuncionario());
        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }

    public FuncionarioResponseDto reverter(Long idFuncionario) throws FuncionarioNaoExisteException {
        // Funcionario funcionarioCache = redisFuncionarioCache.getCache(idFuncionario);
        // if (funcionarioCache == null) {
        //     throw new FuncionarioNaoExisteException("Funcionario nao existe no cache!");
        // }

        // Funcionario funcionario = funcionarioRepository.save(funcionarioCache);
        // redisFuncionarioCache.removeCache(funcionarioCache.getIdFuncionario());
        return modelMapper.map(funcionario, FuncionarioResponseDto.class);
    }
}
