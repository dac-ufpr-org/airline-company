package com.mscontracts.ms_contracts.dto.client;

import lombok.Data;

@Data
public class AddressDTO {

    private String cep;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;
}
