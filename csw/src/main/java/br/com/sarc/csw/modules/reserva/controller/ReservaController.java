package br.com.sarc.csw.modules.reserva.controller;

import br.com.sarc.csw.modules.reserva.dto.ReservaDTO;
import br.com.sarc.csw.modules.reserva.dto.ReservaMapper;
import br.com.sarc.csw.modules.reserva.model.Reserva;
import br.com.sarc.csw.modules.reserva.service.ReservaService;
import jakarta.validation.Valid;
import br.com.sarc.csw.core.exception.MensagemErroDTO;
import br.com.sarc.csw.core.exception.RecursoIndisponivelException;
import br.com.sarc.csw.modules.recurso.service.RecursoService;

import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private RecursoService recursoService;

    // Criar uma nova reserva
    @PreAuthorize("hasRole('PROFESSOR')")
    @PostMapping
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody @Valid ReservaDTO reservaDTO) {
        // Verifica se o recurso está disponível
        boolean recursoDisponivel = recursoService.verificarDisponibilidade(reservaDTO.getId_recurso());
       if (!recursoDisponivel) {
            throw new RecursoIndisponivelException("Recurso não está disponível para reserva.");
        }

        Reserva reserva = ReservaMapper.toEntity(reservaDTO);
        Reserva novaReserva = reservaService.criar(reserva);

        recursoService.atualizarStatus(reservaDTO.getId_recurso(), "RESERVADO");

        return ResponseEntity.ok(ReservaMapper.toDTO(novaReserva));
    }

    // Atualizar uma reserva existente
    @PreAuthorize("hasRole('PROFESSOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> atualizarReserva(@PathVariable Long id, @RequestBody @Valid ReservaDTO reservaDTO) {
        Reserva reserva = ReservaMapper.toEntity(reservaDTO);
        Reserva reservaAtualizada = reservaService.atualizar(id, reserva);
        if (reservaAtualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ReservaMapper.toDTO(reservaAtualizada));
    }

    // Listar todas as reservas
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listarReservas() {
        List<Reserva> reservas = reservaService.listarTodas();
        List<ReservaDTO> reservasDTO = reservas.stream()
                .map(ReservaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obterReserva(@PathVariable Long id) {
        Reserva reserva = reservaService.obterPorId(id);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ReservaMapper.toDTO(reserva));
    }

    // Deletar uma reserva
    @PreAuthorize("hasRole('PROFESSOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReserva(@PathVariable Long id) {
        boolean deletado = reservaService.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}