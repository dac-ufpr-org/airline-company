package com.msreserva.ms_reserva.query;

import com.msreserva.ms_reserva.model.Reservation;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReservationQueryListener {

    @RabbitListener(queues = "reserva.queue")
    public void handleReservationUpdate(Reservation reservation) {
        // Aqui implemente a lógica para atualizar a base de consulta desnormalizada
        System.out.println("Recebido evento de reserva para atualizar consulta: " + reservation.getId());

        // TODO: salvar no banco de consulta (exemplo MongoDB, Redis, etc)
    }
}
