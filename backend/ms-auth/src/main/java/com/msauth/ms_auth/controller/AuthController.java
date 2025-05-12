package com.msauth.ms_auth.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        User user = userRepository.findByLogin(dto.getLogin());
        if (user == null) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        String hashed = PasswordUtils.hashPassword(dto.getSenha(), dto.getLogin());
        if (!user.getSenha().equals(hashed)) {
            return ResponseEntity.status(403).body("Senha incorreta");
        }

        String token = jwtUtil.generateToken(user.getLogin(), user.getTipo());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        String salt = dto.getLogin(); // usar login como salt
        String hashed = PasswordUtils.hashPassword(dto.getSenha(), salt);
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setSenha(hashed);
        user.setTipo(dto.getTipo());
        userRepository.save(user);
        return ResponseEntity.ok("Usuário cadastrado");
    }
}
