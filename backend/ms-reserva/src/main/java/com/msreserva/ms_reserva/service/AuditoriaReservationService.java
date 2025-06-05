package com.msreserva.ms_reserva.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * processar e gerar alertas de auditoria em reservas.
 */
public class AuditoriaReservationService {

    public void processarReservas() {
        List<Reserva> reservas = obterReservas();

        List<String> alertas = new ArrayList<>();

        for (Reserva r : reservas) {
            if (r.estado.equals("CRIADA") && r.dataCriacao.isBefore(LocalDateTime.now().minusDays(3))) {
                alertas.add("Reserva " + r.codigo + " criada há mais de 3 dias.");
            }

            if (r.estado.equals("CANCELADA") && r.checkInRealizado) {
                alertas.add("Reserva " + r.codigo + " cancelada com check-in realizado.");
            }

            if (r.estado.equals("CHECK-IN") && !r.checkInRealizado) {
                alertas.add("Reserva " + r.codigo + " em CHECK-IN sem flag confirmada.");
            }
        }

        imprimirAlertas(alertas);
    }

    private List<Reserva> obterReservas() {
        List<Reserva> lista = new ArrayList<>();
        lista.add(new Reserva("R001", LocalDateTime.now().minusDays(1), "CRIADA", false));
        lista.add(new Reserva("R002", LocalDateTime.now().minusDays(4), "CANCELADA", true));
        lista.add(new Reserva("R003", LocalDateTime.now().minusDays(2), "CHECK-IN", false));
        return lista;
    }

    private void imprimirAlertas(List<String> alertas) {
        if (alertas.isEmpty()) {
            System.out.println("Nenhum alerta encontrado.");
        } else {
            for (String alerta : alertas) {
                System.out.println("[ALERTA] " + alerta);
            }
        }
    }

    // classe interna representando estrutura de reserva 
    private static class Reserva {
        String codigo;
        LocalDateTime dataCriacao;
        String estado;
        boolean checkInRealizado;

        Reserva(String codigo, LocalDateTime dataCriacao, String estado, boolean checkInRealizado) {
            this.codigo = codigo;
            this.dataCriacao = dataCriacao;
            this.estado = estado;
            this.checkInRealizado = checkInRealizado;
        }
    }
}
