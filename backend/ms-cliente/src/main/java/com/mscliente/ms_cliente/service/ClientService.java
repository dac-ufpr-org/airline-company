package com.mscliente.ms_cliente.service;

import com.mscontracts.ms_contracts.dto.client.ClientRequestDTO;
import com.mscontracts.ms_contracts.dto.client.ClientResponseDTO;
import com.mscontracts.ms_contracts.dto.client.AddressDTO;
import com.mscliente.ms_cliente.model.Address;
import com.mscliente.ms_cliente.model.Client;
import com.mscliente.ms_cliente.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio de clientes.
 * Gerencia operações CRUD de clientes no banco de dados PostgreSQL.
 * 
 * Funcionalidades:
 * - Cadastro de cliente com endereço
 * - Consulta de cliente por CPF
 * - Listagem de todos os clientes
 * - Atualização de dados do cliente
 * - Conversão entre entidades e DTOs
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Cadastra um novo cliente no sistema.
     * Cria cliente e endereço associado no banco de dados.
     * 
     * @param dto Dados do cliente para cadastro
     * @return Cliente criado com dados completos
     */
    public ClientResponseDTO cadastrar(ClientRequestDTO dto) {
        // Cria entidade cliente
        Client client = new Client();
        client.setCpf(dto.getCpf());
        client.setEmail(dto.getEmail());
        client.setName(dto.getName());
        client.setMilesBalance(0.0);

        // Cria entidade endereço
        Address address = new Address();
        address.setCep(dto.getCep());
        address.setStreet(dto.getRua());
        address.setNumber(dto.getNumero());
        address.setComplement(dto.getComplemento());
        address.setCity(dto.getCidade());
        address.setState(dto.getEstado());

        // Associa endereço ao cliente
        client.setAddress(address);
        clientRepository.save(client);

        return mapToDto(client);
    }

    /**
     * Busca cliente por CPF.
     * 
     * @param cpf CPF do cliente a ser buscado
     * @return Dados completos do cliente
     * @throws RuntimeException se cliente não for encontrado
     */
    public ClientResponseDTO buscarPorCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return mapToDto(client);
    }

    /**
     * Lista todos os clientes cadastrados no sistema.
     * 
     * @return Lista de todos os clientes
     */
    public List<ClientResponseDTO> listarTodos() {
        return clientRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza dados de um cliente existente.
     * 
     * @param cpf CPF do cliente a ser atualizado
     * @param dto Novos dados do cliente
     * @return Cliente atualizado
     * @throws RuntimeException se cliente não for encontrado
     */
    public ClientResponseDTO atualizar(String cpf, ClientRequestDTO dto) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Atualiza dados básicos do cliente
        client.setEmail(dto.getEmail());
        client.setName(dto.getName());

        // Atualiza dados do endereço
        Address address = client.getAddress();
        address.setCep(dto.getCep());
        address.setStreet(dto.getRua());
        address.setNumber(dto.getNumero());
        address.setComplement(dto.getComplemento());
        address.setCity(dto.getCidade());
        address.setState(dto.getEstado());

        clientRepository.save(client);
        return mapToDto(client);
    }

    /**
     * Converte entidade Client para DTO de resposta.
     * Mapeia dados do cliente e endereço para o formato de resposta.
     * 
     * @param client Entidade cliente do banco de dados
     * @return DTO com dados formatados para resposta
     */
    private ClientResponseDTO mapToDto(Client client) {
        // Mapeia dados do endereço
        Address addr = client.getAddress();
        AddressDTO addrDto = new AddressDTO();
        addrDto.setCep(addr.getCep());
        addrDto.setStreet(addr.getStreet());
        addrDto.setNumber(addr.getNumber());
        addrDto.setComplement(addr.getComplement());
        addrDto.setCity(addr.getCity());
        addrDto.setState(addr.getState());

        // Mapeia dados do cliente
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setCpf(client.getCpf());
        dto.setEmail(client.getEmail());
        dto.setName(client.getName());
        dto.setMilesBalance(client.getMilesBalance());
        dto.setAddress(addrDto);

        return dto;
    }
}
