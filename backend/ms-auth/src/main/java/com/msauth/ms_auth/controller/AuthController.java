package com.msauth.ms_auth.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.msauth.ms_auth.dto.LoginDTO;
import com.msauth.ms_auth.dto.RegisterDTO;
import com.msauth.ms_auth.model.User;
import com.msauth.ms_auth.repository.UserRepository;
import com.msauth.ms_auth.service.UserService;
import com.msauth.ms_auth.util.JwtUtil;
import com.msauth.ms_auth.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador responsável pelos endpoints de autenticação.
 * Gerencia login, registro e validação de usuários.
 * 
 * Endpoints disponíveis:
 * - POST /auth/login: Autenticação de usuário
 * - POST /auth/register: Registro de novo usuário
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Endpoint para autenticação de usuário.
     * Valida credenciais e retorna token JWT se autenticação for bem-sucedida.
     * 
     * @param dto Dados de login (email e senha)
     * @return Token JWT e tipo de usuário se autenticação for bem-sucedida
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        log.info("Tentativa de login para usuário: {}", dto.getLogin());

        // Busca usuário no banco de dados
        User user = userRepository.findByLogin(dto.getLogin());
        if (user == null) {
            log.warn("Tentativa de login com usuário inexistente: {}", dto.getLogin());
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        // Valida senha usando hash + salt
        String hashedInput = PasswordUtils.hashPassword(dto.getSenha(), user.getSalt());

        if (!user.getSenha().equals(hashedInput)) {
            log.warn("Tentativa de login com senha incorreta para usuário: {}", dto.getLogin());
            return ResponseEntity.status(403).body("Senha incorreta");
        }

        // Gera token JWT com informações do usuário
        String token = jwtUtil.generateToken(user.getLogin(), user.getTipo());
        log.info("Login realizado com sucesso para usuário: {}", dto.getLogin());
        
        // Retorna token e tipo de usuário
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("tipo", user.getTipo());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para registro de novo usuário.
     * Cria usuário com senha temporária e envia email.
     * 
     * @param dto Dados de registro (email, senha, tipo)
     * @return Confirmação de registro com senha temporária
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        log.info("Tentativa de registro para usuário: {}", dto.getLogin());
        
        try {
            // Cria usuário e gera senha temporária
            String senhaTemporaria = userService.criarUsuario(dto);
            
            // Retorna confirmação com senha temporária
            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário cadastrado com sucesso");
            response.put("senhaTemporaria", senhaTemporaria);
            
            log.info("Registro realizado com sucesso para usuário: {}", dto.getLogin());
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            // Erro de validação (usuário já existe, dados inválidos, etc.)
            log.warn("Falha no registro para usuário {}: {}", dto.getLogin(), e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Erro interno do servidor
            log.error("Erro interno no registro para usuário {}: {}", dto.getLogin(), e.getMessage());
            return ResponseEntity.status(500).body("Erro interno do servidor");
        }
    }
}
