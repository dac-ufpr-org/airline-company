package com.msvoo.ms_voo.service;
import com.msvoo.ms_voo.entity.Flight;
import org.springframework.stereotype.Service;


@Service

public class FlightService {
 

    public Flight createFlight(Flight flight) {
        //  Lógica para salvar o voo no banco de dados
        return flight; 
    }
    public Flight getFlightById(Long id) {
        //  Lógica para recuperar o voo pelo ID do banco de dados
        return null; 
    }
}


