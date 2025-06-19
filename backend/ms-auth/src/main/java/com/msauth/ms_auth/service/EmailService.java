package com.msauth.ms_auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Serviço de email para envio de senhas temporárias.
 * Implementação mock para demonstração.
 */
@Slf4j
@Service
public class EmailService {

    /**
     * Envia email com senha temporária para o usuário.
     * Em produção, integrar com serviço de email real (SendGrid, AWS SES, etc.)
     */
    public void enviarSenhaTemporaria(String email, String nome, String senhaTemporaria) {
        log.info("=== EMAIL ENVIADO ===");
        log.info("Para: {}", email);
        log.info("Assunto: Sua senha temporária - Airline Company");
        log.info("Corpo:");
        log.info("Olá {},", nome);
        log.info("");
        log.info("Sua conta foi criada com sucesso!");
        log.info("Sua senha temporária é: {}", senhaTemporaria);
        log.info("");
        log.info("Por favor, altere sua senha no primeiro acesso.");
        log.info("");
        log.info("Atenciosamente,");
        log.info("Equipe Airline Company");
        log.info("=== FIM DO EMAIL ===");
        
        // Em produção, aqui seria feita a integração com serviço de email
        // Exemplo com SendGrid:
        // sendGridService.sendEmail(email, "Sua senha temporária", emailContent);
    }

    /**
     * Envia email de notificação de falha no cadastro.
     */
    public void enviarNotificacaoFalha(String email, String motivo) {
        log.info("=== EMAIL DE FALHA ENVIADO ===");
        log.info("Para: {}", email);
        log.info("Assunto: Falha no cadastro - Airline Company");
        log.info("Corpo:");
        log.info("Olá,");
        log.info("");
        log.info("Infelizmente houve uma falha no seu cadastro: {}", motivo);
        log.info("Por favor, tente novamente ou entre em contato com o suporte.");
        log.info("");
        log.info("Atenciosamente,");
        log.info("Equipe Airline Company");
        log.info("=== FIM DO EMAIL ===");
    }
} 