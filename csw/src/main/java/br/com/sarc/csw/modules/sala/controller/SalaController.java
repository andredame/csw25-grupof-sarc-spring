package br.com.sarc.csw.modules.sala.controller;

import br.com.sarc.csw.modules.sala.dto.SalaRequestDTO;
import br.com.sarc.csw.modules.sala.dto.SalaResponseDTO;
import br.com.sarc.csw.modules.sala.dto.SalaMapper;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.sala.service.SalaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api") // ALTERADO: Define um RequestMapping base para '/api'
@SecurityRequirement(name = "bearerAuth")
public class SalaController {

    @Autowired
    private SalaService salaService;

    // NOVO ENDPOINT: Lista TODAS as salas (global)
    // Mapeado para /api/salas
    @GetMapping("/salas")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'ALUNO', 'COORDENADOR')")
    public ResponseEntity<List<SalaResponseDTO>> listarTodasSalas() {
        try {
            List<Sala> salas = salaService.listarTodasSalas(); // Crie este método no SalaService
            List<SalaResponseDTO> salasDTO = salas.stream()
                    .map(SalaMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(salasDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar todas as salas", e);
        }
    }

    // Endpoint: Adicionar Sala (específico de prédio)
    // Mapeado para /api/predios/{predioId}/salas
    @PostMapping("/predios/{predioId}/salas") // ALTERADO: Adicionado '/predios/{predioId}'
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaResponseDTO> adicionarSala(@PathVariable Long predioId, @Valid @RequestBody SalaRequestDTO salaDTO) {
        try {
            Sala sala = SalaMapper.toEntity(salaDTO);
            Sala novaSala = salaService.criarSala(predioId, sala);
            return ResponseEntity.status(HttpStatus.CREATED).body(SalaMapper.toResponseDTO(novaSala));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao adicionar sala", e);
        }
    }

    // Endpoint: Editar Sala (geral, por Sala ID)
    // Mapeado para /api/salas/{salaId}
    @PutMapping("/salas/{salaId}") // ALTERADO: Mudado para /salas/{salaId}, não precisa mais de predioId aqui
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaResponseDTO> editarSala(@PathVariable Long salaId, @Valid @RequestBody SalaRequestDTO salaDTO) {
        try {
            Sala sala = SalaMapper.toEntity(salaDTO);
            // O serviço deve ser capaz de encontrar e atualizar a sala apenas com o salaId
            Sala salaAtualizada = salaService.atualizarSala(salaId, sala); // Crie/ajuste este método no SalaService
            return ResponseEntity.ok(SalaMapper.toResponseDTO(salaAtualizada));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao editar sala", e);
        }
    }

    // Endpoint: Listar Salas por Prédio (específico de prédio)
    // Mapeado para /api/predios/{predioId}/salas
    @GetMapping("/predios/{predioId}/salas") // ALTERADO: Adicionado '/predios/{predioId}'
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'ALUNO')")
    public ResponseEntity<List<SalaResponseDTO>> listarSalasPorPredio(@PathVariable Long predioId) {
        try {
            List<Sala> salas = salaService.listarSalasPorPredio(predioId);
            List<SalaResponseDTO> salasDTO = salas.stream()
                    .map(SalaMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(salasDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar salas", e);
        }
    }

    // Endpoint: Buscar Sala por ID (específico de prédio)
    // Mapeado para /api/predios/{predioId}/salas/{salaId}
    @GetMapping("/predios/{predioId}/salas/{salaId}") // ALTERADO: Adicionado '/predios/{predioId}'
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'ALUNO')")
    public ResponseEntity<SalaResponseDTO> buscarSalaPorId(@PathVariable Long predioId, @PathVariable Long salaId) {
        try {
            Sala sala = salaService.buscarSalaPorPredioEId(predioId, salaId);
            return ResponseEntity.ok(SalaMapper.toResponseDTO(sala));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar sala", e);
        }
    }

    // Endpoint: Deletar Sala (geral, por Sala ID)
    // Mapeado para /api/salas/{salaId}
    @DeleteMapping("/salas/{salaId}") // ALTERADO: Mudado para /salas/{salaId}, não precisa mais de predioId aqui
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarSala(@PathVariable Long salaId) { // Removido predioId do parâmetro
        try {
            salaService.deletarSala(salaId); // Crie/ajuste este método no SalaService
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar sala", e);
        }
    }

    // Endpoint: Filtrar Salas por Capacidade (específico de prédio)
    // Mapeado para /api/predios/{predioId}/salas/filtro
    @GetMapping("/predios/{predioId}/salas/filtro") // ALTERADO: Adicionado '/predios/{predioId}'
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'ALUNO')")
    public ResponseEntity<List<SalaResponseDTO>> filtrarSalasPorCapacidade(
            @PathVariable Long predioId,
            @RequestParam int capacidadeMinima
    ) {
        try {
            List<Sala> salas = salaService.filtrarPorCapacidade(predioId, capacidadeMinima);
            List<SalaResponseDTO> salasDTO = salas.stream()
                    .map(SalaMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(salasDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao filtrar salas", e);
        }
    }
}