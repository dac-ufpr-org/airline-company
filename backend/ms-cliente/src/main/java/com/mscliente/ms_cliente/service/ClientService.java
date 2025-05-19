package com.mscliente.ms_cliente.service;

import com.mscliente.ms_cliente.dto.ClientRequestDto;
import com.mscliente.ms_cliente.dto.ClientResponseDto;
import com.mscliente.ms_cliente.dto.AddressDto;
import com.mscliente.ms_cliente.model.Address;
import com.mscliente.ms_cliente.model.Client;
import com.mscliente.ms_cliente.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientResponseDto cadastrar(ClientRequestDto dto) {
        Client client = new Client();
        client.setCpf(dto.getCpf());
        client.setEmail(dto.getEmail());
        client.setName(dto.getName());
        client.setMilesBalance(0.0);

        Address address = new Address();
        address.setCep(dto.getCep());
        address.setStreet(dto.getRua());
        address.setNumber(dto.getNumero());
        address.setComplement(dto.getComplemento());
        address.setCity(dto.getCidade());
        address.setState(dto.getEstado());
        address.setNeighborhood("Centro"); // default se não vier do frontend

        client.setAddress(address);
        clientRepository.save(client);

        return mapToDto(client);
    }

    public ClientResponseDto buscarPorCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return mapToDto(client);
    }

    public List<ClientResponseDto> listarTodos() {
        return clientRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ClientResponseDto atualizar(String cpf, ClientRequestDto dto) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        client.setEmail(dto.getEmail());
        client.setName(dto.getName());

        Address address = client.getAddress();
        address.setCep(dto.getCep());
        address.setStreet(dto.getRua());
        address.setNumber(dto.getNumero());
        address.setComplement(dto.getComplemento());
        address.setCity(dto.getCidade());
        address.setState(dto.getEstado());
        address.setNeighborhood("Centro");

        clientRepository.save(client);
        return mapToDto(client);
    }

    private ClientResponseDto mapToDto(Client client) {
        Address addr = client.getAddress();
        AddressDto addrDto = new AddressDto();
        addrDto.setCep(addr.getCep());
        addrDto.setStreet(addr.getStreet());
        addrDto.setNumber(addr.getNumber());
        addrDto.setComplement(addr.getComplement());
        addrDto.setCity(addr.getCity());
        addrDto.setState(addr.getState());
        addrDto.setNeighborhood(addr.getNeighborhood());

        ClientResponseDto dto = new ClientResponseDto();
        dto.setCpf(client.getCpf());
        dto.setEmail(client.getEmail());
        dto.setName(client.getName());
        dto.setMilesBalance(client.getMilesBalance());
        dto.setAddress(addrDto);

        return dto;
    }
}