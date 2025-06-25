// csw/src/main/java/br/com/sarc/csw/modules/reserva/controller/ReservaController.java
package br.com.sarc.csw.modules.reserva.controller;

import br.com.sarc.csw.modules.reserva.dto.ReservaDTO;
import br.com.sarc.csw.modules.reserva.dto.ReservaMapper;
import br.com.sarc.csw.modules.reserva.dto.ReservaResponseDTO;
import br.com.sarc.csw.modules.reserva.model.Reserva;
import br.com.sarc.csw.modules.reserva.service.ReservaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ALUNO', 'COORDENADOR', 'ADMIN')")
    public ResponseEntity<List<ReservaResponseDTO>> listarTodasReservas() {
        try {
            List<Reserva> reservas = reservaService.listarTodasReservas();
            return ResponseEntity.ok(reservas.stream().map(ReservaMapper::toResponseDTO).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar reservas.", e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ALUNO', 'COORDENADOR', 'ADMIN')")
    public ResponseEntity<ReservaDTO> obterReservaPorId(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.obterPorId(id);
            if (reserva == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada.");
            }
            return ResponseEntity.ok(ReservaMapper.toDTO(reserva));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter reserva.", e);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')") // Apenas professores podem criar reservas
    public ResponseEntity<ReservaDTO> criarReserva(@RequestBody @Valid ReservaDTO reservaDTO) { // ALTERADO: Recebe ReservaDTO
        try {
            Reserva reserva = ReservaMapper.toEntity(reservaDTO); // Converte DTO para entidade
            Reserva novaReserva = reservaService.criar(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(ReservaMapper.toDTO(novaReserva));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar reserva.", e);
        }
    }

   @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarReservasPorProfessor(@PathVariable UUID professorId) {
        try {
            List<Reserva> reservas = reservaService.listarReservasPorProfessor(professorId);
            return ResponseEntity.ok(reservas.stream()
                    .map(ReservaMapper::toResponseDTO)
                    .collect(Collectors.toList())); // ✅ Aqui também
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar reservas do professor.", e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<ReservaDTO> atualizarReserva(@PathVariable Long id, @RequestBody @Valid ReservaDTO reservaDTO) {
        try {
            Reserva reservaAtualizada = ReservaMapper.toEntity(reservaDTO);
            Reserva reservaSalva = reservaService.atualizar(id, reservaAtualizada);
            if (reservaSalva == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada para atualização.");
            }
            return ResponseEntity.ok(ReservaMapper.toDTO(reservaSalva));
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar reserva.", e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> deletarReserva(@PathVariable Long id) {
        try {
            boolean deletado = reservaService.deletar(id);
            if (!deletado) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada para exclusão.");
            }
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar reserva.", e);
        }
    }
    
}