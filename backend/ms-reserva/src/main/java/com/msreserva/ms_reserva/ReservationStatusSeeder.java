// package com.msreserva.ms_reserva;

// import com.msreserva.ms_reserva.model.ReservationStatusEntity;
// import com.msreserva.ms_reserva.repository.ReservationStatusRepository;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.Arrays;
// import java.util.List;

// @Component
// public class ReservationStatusSeeder implements CommandLineRunner {

//     private final ReservationStatusRepository repository;

//     public ReservationStatusSeeder(ReservationStatusRepository repository) {
//         this.repository = repository;
//     }

//     @Override
//     public void run(String... args) {
//         if (repository.count() == 0) {
//             List<ReservationStatusEntity> statusList = Arrays.asList(
//                 new ReservationStatusEntity("CRIADA", "Reserva criada"),
//                 new ReservationStatusEntity("CHECK_IN", "Check-in realizado"),
//                 new ReservationStatusEntity("CANCELADA", "Reserva cancelada"),
//                 new ReservationStatusEntity("CANCELADA_VOO", "Cancelada por cancelamento de voo"),
//                 new ReservationStatusEntity("EMBARCADA", "Passageiro embarcado"),
//                 new ReservationStatusEntity("REALIZADA", "Voo realizado"),
//                 new ReservationStatusEntity("NAO_REALIZADA", "Voo não realizado")
//             );
//             repository.saveAll(statusList);
//         }
//     }
// }
