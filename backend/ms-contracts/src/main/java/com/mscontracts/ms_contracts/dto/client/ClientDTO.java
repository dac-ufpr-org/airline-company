package com.mscontracts.ms_contracts.dto.client;

import lombok.Data;

@Data
public class ClientDTO {

    private String cpf;
    private String name;
    private String email;
    private AddressDTO address;
    private Integer miles;

}
