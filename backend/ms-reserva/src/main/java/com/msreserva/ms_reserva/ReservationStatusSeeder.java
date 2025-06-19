package com.msreserva.ms_reserva;

import com.msreserva.ms_reserva.model.ReservationStatusEntity;
import com.msreserva.ms_reserva.repository.ReservationStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;

@Component
public class ReservationStatusSeeder implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ReservationStatusSeeder.class);
    private final ReservationStatusRepository repository;
    public ReservationStatusSeeder(ReservationStatusRepository repository) {
        this.repository = repository;
    }
    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            logger.info("[SEED] Inserindo ReservationStatus iniciais...");
            List<ReservationStatusEntity> statusList = Arrays.asList(
                new ReservationStatusEntity("CRIADA", "Reserva criada"),
                new ReservationStatusEntity("CHECK_IN", "Check-in realizado"),
                new ReservationStatusEntity("CANCELADA", "Reserva cancelada"),
                new ReservationStatusEntity("CANCELADA_VOO", "Cancelada por cancelamento de voo"),
                new ReservationStatusEntity("EMBARCADA", "Passageiro embarcado"),
                new ReservationStatusEntity("REALIZADA", "Voo realizado"),
                new ReservationStatusEntity("NAO_REALIZADA", "Voo não realizado")
            );
            repository.saveAll(statusList);
            logger.info("[SEED] ReservationStatus inseridos com sucesso.");
        }
    }
}
