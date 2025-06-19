package com.msauth.ms_auth.service;

import com.msauth.ms_auth.dto.RegisterDTO;
import com.msauth.ms_auth.model.User;
import com.msauth.ms_auth.repository.UserRepository;
import com.msauth.ms_auth.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pela lógica de negócio de usuários.
 * Gerencia criação, remoção e validação de usuários no sistema de autenticação.
 * 
 * Funcionalidades:
 * - Criação de usuário com senha temporária
 * - Remoção de usuário (para compensação SAGA)
 * - Validação de dados de usuário
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Cria um novo usuário com senha aleatória.
     * Gera senha temporária de 4 dígitos e envia por email.
     * 
     * @param dto Dados do usuário para criação
     * @return Senha temporária gerada
     * @throws RuntimeException se usuário já existir
     */
    @Transactional
    public String criarUsuario(RegisterDTO dto) {
        log.info("🔐 Criando usuário: {}", dto.getLogin());
        
        // Verifica se usuário já existe
        if (userRepository.findByLogin(dto.getLogin()) != null) {
            log.warn("⚠️ Tentativa de criar usuário que já existe: {}", dto.getLogin());
            throw new RuntimeException("Usuário já existe");
        }

        // Gera salt único e senha temporária
        String salt = PasswordUtils.generateSalt();
        String senhaAleatoria = PasswordUtils.gerarSenhaAleatoria();
        String hashed = PasswordUtils.hashPassword(senhaAleatoria, salt);

        // Cria e salva usuário
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setSenha(hashed);
        user.setSalt(salt);
        user.setTipo(dto.getTipo());

        userRepository.save(user);
        
        log.info("✅ Usuário criado com sucesso: {} - Senha temporária: {}", dto.getLogin(), senhaAleatoria);
        return senhaAleatoria;
    }

    /**
     * Remove um usuário pelo login.
     * Usado para compensação em caso de falha na SAGA.
     * 
     * @param login Email/login do usuário a ser removido
     * @throws RuntimeException se usuário não for encontrado
     */
    @Transactional
    public void removerUsuario(String login) {
        log.info("🗑️ Removendo usuário: {}", login);
        
        User user = userRepository.findByLogin(login);
        if (user == null) {
            log.warn("⚠️ Tentativa de remover usuário inexistente: {}", login);
            throw new RuntimeException("Usuário não encontrado");
        }
        
        userRepository.delete(user);
        log.info("✅ Usuário removido com sucesso: {}", login);
    }

    /**
     * Busca um usuário pelo login.
     */
    public User buscarPorLogin(String login) {
        return userRepository.findByLogin(login);
    }
} 