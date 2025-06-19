package com.mssagas.ms_sagas.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;
import com.mscontracts.ms_contracts.dto.saga.SagaStateDTO;
import com.mscontracts.ms_contracts.enums.SagaStatus;
import com.mscontracts.ms_contracts.events.ClienteCriadoEvent;
import com.mscontracts.ms_contracts.events.UsuarioCriadoEvent;
import com.mssagas.ms_sagas.config.RabbitMQSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroClienteSagaServiceTest {

    @Mock
    private RabbitMQSender rabbitMQSender;

    @Mock
    private ObjectMapper objectMapper;

    private CadastroClienteSagaService sagaService;

    @BeforeEach
    void setUp() {
        sagaService = new CadastroClienteSagaService(rabbitMQSender, objectMapper);
    }

    @Test
    void iniciarSagaAutocadastro_DeveCriarCorrelationIdEEnviarEvento() throws Exception {
        // Arrange
        ClientRequestDTO dadosCliente = new ClientRequestDTO();
        dadosCliente.setEmail("teste@email.com");
        dadosCliente.setName("Teste");
        dadosCliente.setCpf("12345678901");

        when(objectMapper.writeValueAsString(any())).thenReturn("dados serializados");

        // Act
        String correlationId = sagaService.iniciarSagaAutocadastro(dadosCliente);

        // Assert
        assertNotNull(correlationId);
        assertFalse(correlationId.isEmpty());
        
        verify(rabbitMQSender).send(eq("auth.queue"), any());
        verify(objectMapper).writeValueAsString(dadosCliente);
    }

    @Test
    void processarUsuarioCriado_DeveAtualizarEstadoEEnviarEventoCliente() throws Exception {
        // Arrange
        UsuarioCriadoEvent event = new UsuarioCriadoEvent();
        event.setEmail("teste@email.com");
        event.setNome("Teste");
        event.setCorrelationId("test-correlation-id");
        event.setSenhaTemporaria("1234");

        ClientRequestDTO dadosCliente = new ClientRequestDTO();
        dadosCliente.setEmail("teste@email.com");
        dadosCliente.setName("Teste");
        dadosCliente.setCpf("12345678901");

        when(objectMapper.readValue(anyString(), eq(ClientRequestDTO.class))).thenReturn(dadosCliente);

        // Primeiro, iniciar uma SAGA para ter estado
        when(objectMapper.writeValueAsString(any())).thenReturn("dados serializados");
        sagaService.iniciarSagaAutocadastro(dadosCliente);

        // Act
        sagaService.processarUsuarioCriado(event);

        // Assert
        verify(rabbitMQSender).send(eq("cliente.queue"), any());
        
        SagaStateDTO estado = sagaService.consultarEstadoSaga("test-correlation-id");
        assertNotNull(estado);
        assertEquals(SagaStatus.USUARIO_CRIADO, estado.getStatus());
    }

    @Test
    void processarClienteCriado_DeveCompletarSaga() throws Exception {
        // Arrange
        ClienteCriadoEvent event = new ClienteCriadoEvent();
        event.setEmail("teste@email.com");
        event.setNome("Teste");
        event.setCorrelationId("test-correlation-id");
        event.setCpf("12345678901");

        ClientRequestDTO dadosCliente = new ClientRequestDTO();
        dadosCliente.setEmail("teste@email.com");
        dadosCliente.setName("Teste");
        dadosCliente.setCpf("12345678901");

        when(objectMapper.readValue(anyString(), eq(ClientRequestDTO.class))).thenReturn(dadosCliente);

        // Primeiro, iniciar uma SAGA para ter estado
        when(objectMapper.writeValueAsString(any())).thenReturn("dados serializados");
        sagaService.iniciarSagaAutocadastro(dadosCliente);

        // Act
        sagaService.processarClienteCriado(event);

        // Assert
        SagaStateDTO estado = sagaService.consultarEstadoSaga("test-correlation-id");
        assertNotNull(estado);
        assertEquals(SagaStatus.COMPLETADA, estado.getStatus());
    }

    @Test
    void consultarEstadoSaga_QuandoNaoExiste_DeveRetornarNull() {
        // Act
        SagaStateDTO estado = sagaService.consultarEstadoSaga("correlation-id-inexistente");

        // Assert
        assertNull(estado);
    }
} 