package com.msauth.ms_auth.listener;

import com.msauth.ms_auth.dto.RegisterDTO;
import com.msauth.ms_auth.service.EmailService;
import com.msauth.ms_auth.service.UserService;
import com.mscontracts.ms_contracts.events.CompensarUsuarioEvent;
import com.mscontracts.ms_contracts.events.CriarUsuarioEvent;
import com.mscontracts.ms_contracts.events.UsuarioCriadoEvent;
import com.mscontracts.ms_contracts.queues.QueueNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Listener responsável por receber eventos relacionados à autenticação.
 * Participa da SAGA de autocadastro como um dos serviços participantes.
 * 
 * Eventos processados:
 * - CriarUsuarioEvent: Cria usuário e envia email com senha temporária
 * - CompensarUsuarioEvent: Remove usuário em caso de falha na SAGA
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthListener {

    private final UserService userService;
    private final EmailService emailService;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Recebe evento para criar usuário como parte da SAGA de autocadastro.
     * 
     * Fluxo:
     * 1. Recebe CriarUsuarioEvent do orchestrator
     * 2. Cria usuário no MongoDB com senha temporária
     * 3. Envia email com senha temporária
     * 4. Envia UsuarioCriadoEvent para o orchestrator
     * 
     * @param event Evento de criação de usuário
     */
    @RabbitListener(queues = QueueNames.AUTH_QUEUE)
    public void criarUsuario(CriarUsuarioEvent event) {
        log.info("📥 Auth recebeu solicitação para criar usuário: {} - correlationId: {}", 
                event.getEmail(), event.getCorrelationId());
        
        try {
            // Criar DTO para o serviço
            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setLogin(event.getEmail());
            registerDTO.setSenha(event.getSenha()); // Pode ser null, será gerada
            registerDTO.setTipo("CLIENTE");
            
            // Criar usuário e obter senha temporária
            log.info("🔐 Criando usuário no sistema de autenticação: {}", event.getEmail());
            String senhaTemporaria = userService.criarUsuario(registerDTO);
            
            // Enviar email com senha temporária
            log.info("📧 Enviando email com senha temporária para: {}", event.getEmail());
            emailService.enviarSenhaTemporaria(event.getEmail(), event.getNome(), senhaTemporaria);
            
            // Enviar evento de sucesso para o orchestrator
            UsuarioCriadoEvent usuarioCriadoEvent = new UsuarioCriadoEvent(
                event.getEmail(),
                event.getNome(),
                event.getCorrelationId(),
                senhaTemporaria
            );
            
            log.info("📤 Enviando UsuarioCriadoEvent para orchestrator: {}", event.getCorrelationId());
            rabbitTemplate.convertAndSend(QueueNames.ORCHESTRATOR_QUEUE, usuarioCriadoEvent);
            
        } catch (Exception e) {
            log.error("❌ Erro ao criar usuário: {} - correlationId: {}", 
                    e.getMessage(), event.getCorrelationId());
            
            // Enviar email de notificação de falha
            log.warn("📧 Enviando email de notificação de falha para: {}", event.getEmail());
            emailService.enviarNotificacaoFalha(event.getEmail(), e.getMessage());
            
            // Enviar evento de falha para o orchestrator
            String errorMessage = "Falha na criação de usuário: " + e.getMessage();
            log.error("📤 Enviando evento de falha para orchestrator: {}", errorMessage);
            rabbitTemplate.convertAndSend(QueueNames.ORCHESTRATOR_QUEUE, errorMessage);
        }
    }

    /**
     * Recebe evento de compensação para remover usuário criado.
     * Executado quando a SAGA falha e precisa reverter operações já realizadas.
     * 
     * @param event Evento de compensação com dados do usuário a ser removido
     */
    @RabbitListener(queues = QueueNames.COMPENSACAO_QUEUE)
    public void compensarUsuario(CompensarUsuarioEvent event) {
        log.info("🔄 Auth recebeu evento de compensação para usuário: {} - correlationId: {}", 
                event.getEmail(), event.getCorrelationId());
        
        try {
            if (event.getEmail() != null) {
                log.info("🗑️ Removendo usuário como parte da compensação: {}", event.getEmail());
                userService.removerUsuario(event.getEmail());
                log.info("✅ Compensação executada com sucesso para usuário: {}", event.getEmail());
            } else {
                log.warn("⚠️ Email não fornecido para compensação - correlationId: {}", event.getCorrelationId());
            }
        } catch (Exception e) {
            log.error("❌ Erro ao executar compensação: {} - correlationId: {}", 
                    e.getMessage(), event.getCorrelationId());
        }
    }
}
