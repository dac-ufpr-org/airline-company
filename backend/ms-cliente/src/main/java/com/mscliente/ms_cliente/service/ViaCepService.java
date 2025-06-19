package com.mscliente.ms_cliente.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mscontracts.ms_contracts.dto.client.AddressDTO;

@Service
public class ViaCepService {

    public AddressDTO consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return new RestTemplate().getForObject(url, AddressDTO.class);
    }
}
