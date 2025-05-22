package com.mscliente.ms_cliente.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mscliente.ms_cliente.dto.AddressDto;

@Service
public class ViaCepService {

    public AddressDto consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return new RestTemplate().getForObject(url, AddressDto.class);
    }
}
