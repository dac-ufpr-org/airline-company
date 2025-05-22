package main.java.com.msreserva.ms_reserva.controller;

import com.msreserva.ms_reserva.entity.Reserva;
import com.msreserva.ms_reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;




@RestController
@RequestMapping("/reservas")
public class ReservationController {
    

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public Reserva criar(@RequestBody Reserva reserva) {
        return reservaService.salvar(reserva);
    }

    @GetMapping
    public List<Reserva> listarTodos() {
        return reservaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Reserva> buscarPorId(@PathVariable Long id) {
        return reservaService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        reservaService.deletar(id);
    }
}
