package br.com.sarc.csw.modules.reserva.controller;

import br.com.sarc.csw.modules.reserva.dto.ReservaDTO;
import br.com.sarc.csw.modules.reserva.dto.ReservaMapper;
import br.com.sarc.csw.modules.reserva.model.Reserva;
import br.com.sarc.csw.modules.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listarReservas() {
        List<Reserva> reservas = reservaService.listarTodas();
        List<ReservaDTO> reservasDTO = reservas.stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obterReserva(@PathVariable Integer id) {
        Reserva reserva = reservaService.obterPorId(id);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ReservaMapper.toDTO(reserva));
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody ReservaDTO reservaDTO) {
        Reserva reserva = ReservaMapper.toEntity(reservaDTO);
        Reserva novaReserva = reservaService.criar(reserva);
        return ResponseEntity.ok(ReservaMapper.toDTO(novaReserva));
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> atualizarReserva(@PathVariable Integer id, @RequestBody ReservaDTO reservaDTO) {
        Reserva reserva = ReservaMapper.toEntity(reservaDTO);
        Reserva reservaAtualizada = reservaService.atualizar(id, reserva);
        if (reservaAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ReservaMapper.toDTO(reservaAtualizada));
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReserva(@PathVariable Integer id) {
        boolean deletado = reservaService.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
