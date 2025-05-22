package main.java.com.msreserva.ms_reserva.dto;
import com.mscliente.ms_cliente.dto.AddressDto;

import lombok.Data;



public class ReservationDto {
   @Data
   public class ReservationRequestDto {
       private String cpf;
       private String email;
       private String name;
       private Double milesBalance;
       private AddressDto address;
       private Long flightId;    
       private String flightDate;
       private String flightTime;     
       private Integer quantidade;   
       private Integer milesUsed; 
}

};