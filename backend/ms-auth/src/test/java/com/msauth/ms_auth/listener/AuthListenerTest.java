package com.msauth.ms_auth.listener;

import com.msauth.ms_auth.service.EmailService;
import com.msauth.ms_auth.service.UserService;
import com.mscontracts.ms_contracts.events.CompensarUsuarioEvent;
import com.mscontracts.ms_contracts.events.CriarUsuarioEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthListenerTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private AuthListener authListener;

    @BeforeEach
    void setUp() {
        authListener = new AuthListener(userService, emailService, rabbitTemplate);
    }

    @Test
    void criarUsuario_QuandoSucesso_DeveEnviarEventoSucesso() {
        // Arrange
        CriarUsuarioEvent event = new CriarUsuarioEvent();
        event.setEmail("teste@email.com");
        event.setNome("Teste");
        event.setCorrelationId("test-correlation-id");

        when(userService.criarUsuario(any())).thenReturn("1234");

        // Act
        authListener.criarUsuario(event);

        // Assert
        verify(userService).criarUsuario(any());
        verify(emailService).enviarSenhaTemporaria(eq("teste@email.com"), eq("Teste"), eq("1234"));
        verify(rabbitTemplate).convertAndSend(anyString(), any(Object.class));
    }

    @Test
    void criarUsuario_QuandoFalha_DeveEnviarEventoFalha() {
        // Arrange
        CriarUsuarioEvent event = new CriarUsuarioEvent();
        event.setEmail("teste@email.com");
        event.setNome("Teste");
        event.setCorrelationId("test-correlation-id");

        when(userService.criarUsuario(any())).thenThrow(new RuntimeException("Erro de teste"));

        // Act
        authListener.criarUsuario(event);

        // Assert
        verify(userService).criarUsuario(any());
        verify(emailService).enviarNotificacaoFalha(eq("teste@email.com"), eq("Erro de teste"));
        verify(rabbitTemplate).convertAndSend(anyString(), any(Object.class));
    }

    @Test
    void compensarUsuario_QuandoEmailValido_DeveRemoverUsuario() {
        // Arrange
        CompensarUsuarioEvent event = new CompensarUsuarioEvent();
        event.setEmail("teste@email.com");
        event.setCorrelationId("test-correlation-id");
        event.setMotivo("Teste de compensação");

        // Act
        authListener.compensarUsuario(event);

        // Assert
        verify(userService).removerUsuario("teste@email.com");
    }

    @Test
    void compensarUsuario_QuandoEmailNulo_NaoDeveRemoverUsuario() {
        // Arrange
        CompensarUsuarioEvent event = new CompensarUsuarioEvent();
        event.setEmail(null);
        event.setCorrelationId("test-correlation-id");
        event.setMotivo("Teste de compensação");

        // Act
        authListener.compensarUsuario(event);

        // Assert
        verify(userService, never()).removerUsuario(any());
    }
} 