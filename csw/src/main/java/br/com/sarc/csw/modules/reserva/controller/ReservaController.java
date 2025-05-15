package br.com.sarc.csw.modules.reserva.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sarc.csw.core.exception.RecursoIndisponivelException;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.aula.service.AulaService;
import br.com.sarc.csw.modules.recurso.service.RecursoService;
import br.com.sarc.csw.modules.reserva.dto.ReservaDTO;
import br.com.sarc.csw.modules.reserva.dto.ReservaMapper;
import br.com.sarc.csw.modules.reserva.dto.ReservaResponseDTO;
import br.com.sarc.csw.modules.reserva.model.Reserva;
import br.com.sarc.csw.modules.reserva.service.ReservaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private AulaService aulaService;

    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<ReservaResponseDTO> criarReserva(@RequestBody @Valid ReservaDTO reservaDTO) {
        Aula aula = aulaService.buscarPorId(reservaDTO.getId_aula());
        if (!reservaService.recursoDisponivelParaAula(reservaDTO.getId_recurso(), aula.getData(), aula.getPeriodo())) {
            throw new RecursoIndisponivelException("Recurso já reservado para esse horário.");
        }
        Reserva reserva = ReservaMapper.toEntity(reservaDTO);
        Reserva novaReserva = reservaService.criar(reserva);
        return ResponseEntity.ok(ReservaMapper.toResponseDTO(novaReserva));
    }

    // Atualizar uma reserva existente
    @PreAuthorize("hasRole('PROFESSOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> atualizarReserva(@PathVariable Long id, @RequestBody @Valid ReservaDTO reservaDTO) {
        Reserva reserva = ReservaMapper.toEntity(reservaDTO);
        Reserva reservaAtualizada = reservaService.atualizar(id, reserva);
        if (reservaAtualizada == null) {
            throw new IllegalArgumentException("Reserva não encontrada para o ID fornecido.");
        }
        return ResponseEntity.ok(ReservaMapper.toResponseDTO(reservaAtualizada));
    }

    // Listar todas as reservas
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        List<Reserva> reservas = reservaService.listarTodas();
        List<ReservaResponseDTO> reservasDTO = reservas.stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obterReserva(@PathVariable Long id) {
        Reserva reserva = reservaService.obterPorId(id);
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada para o ID fornecido.");
        }
        return ResponseEntity.ok(ReservaMapper.toResponseDTO(reserva));
    }

    // Deletar uma reserva
    @PreAuthorize("hasRole('PROFESSOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReserva(@PathVariable Long id) {
        boolean deletado = reservaService.deletar(id);
        if (!deletado) {
            throw new IllegalArgumentException("Reserva não encontrada para o ID fornecido.");
        }
        return ResponseEntity.noContent().build();
    }
}