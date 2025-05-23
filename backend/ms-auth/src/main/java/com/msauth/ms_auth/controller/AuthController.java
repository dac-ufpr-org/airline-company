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
import com.msauth.ms_auth.util.JwtUtil;
import com.msauth.ms_auth.util.PasswordUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    //@PostMapping("/login")
    //public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
    //    User user = userRepository.findByLogin(dto.getLogin());
    //    if (user == null) {
    //        return ResponseEntity.status(401).body("Usuário não encontrado");
    //    }
//
    //    // Verificação corrigida:
    //    String hashedInput = PasswordUtils.hashPassword(dto.getSenha(), user.getSalt());
    //    if (!user.getSenha().equals(hashedInput)) {
    //        return ResponseEntity.status(403).body("Senha incorreta");
    //    }
//
    //    String token = jwtUtil.generateToken(user.getLogin(), user.getTipo());
    //    return ResponseEntity.ok(Collections.singletonMap("token", token));
    //}
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        System.out.println("Tentativa de login: " + dto.getLogin()); // Log

        User user = userRepository.findByLogin(dto.getLogin());
        if (user == null) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        System.out.println("Senha armazenada: " + user.getSenha()); // Log
        System.out.println("Salt armazenado: " + user.getSalt()); // Log

        String hashedInput = PasswordUtils.hashPassword(dto.getSenha(), user.getSalt());
        System.out.println("Senha hasheada: " + hashedInput); // Log

        if (!user.getSenha().equals(hashedInput)) {
            System.out.println("Falha na comparação de hashes"); // Log
            return ResponseEntity.status(403).body("Senha incorreta");
        }

        String token = jwtUtil.generateToken(user.getLogin(), user.getTipo());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        if (userRepository.findByLogin(dto.getLogin()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        String salt = PasswordUtils.generateSalt();
        String senhaAleatoria = PasswordUtils.gerarSenhaAleatoria();
        String hashed = PasswordUtils.hashPassword(senhaAleatoria, salt);

        User user = new User();
        user.setLogin(dto.getLogin());
        user.setSenha(hashed);
        user.setSalt(salt);
        user.setTipo(dto.getTipo());

        userRepository.save(user);

        //return ResponseEntity.ok("Usuário cadastrado com sucesso");
        // Retorno com ambos: mensagem + senha temporária
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário cadastrado com sucesso");
        response.put("senhaTemporaria", senhaAleatoria); // Apenas para testes

        return ResponseEntity.ok(response);
    }
}
