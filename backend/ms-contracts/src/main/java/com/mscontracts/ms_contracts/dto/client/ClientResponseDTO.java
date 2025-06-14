package com.mscontracts.ms_contracts.dto.client;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientResponse extends ClientDTO {

    private Double milesBalance;
    private String userId;
    private String userType;
}
