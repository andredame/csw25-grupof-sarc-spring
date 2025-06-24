package br.com.sarc.csw.modules.disciplina.controller;

import java.util.List;
import java.util.UUID; // Not directly used but good to keep if related to security context

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException; // Import for custom exceptions

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import br.com.sarc.csw.modules.disciplina.dto.DisciplinaMapper;
import br.com.sarc.csw.modules.disciplina.service.DisciplinaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @GetMapping
    @PreAuthorize("hasRole('COORDENADOR') or hasAnyRole('PROFESSOR', 'ALUNO')") // Broaden access for listing
    public ResponseEntity<List<DisciplinaDTO>> listarDisciplinas() {
        try {
            List<DisciplinaDTO> disciplinas = disciplinaService.listarTodas() // Changed to listarTodas
                    .stream()
                    .map(DisciplinaMapper::toDTO)
                    .toList();
            return ResponseEntity.ok(disciplinas);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar disciplinas", e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR') or hasAnyRole('PROFESSOR', 'ALUNO')") // Broaden access for viewing
    public ResponseEntity<DisciplinaDTO> obterDisciplina(@PathVariable Long id) {
        try {
            return disciplinaService.obterPorId(id) // Changed to obterPorId
                    .map(disciplina -> ResponseEntity.ok(DisciplinaMapper.toDTO(disciplina)))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada para o ID fornecido."));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e); // Catch specific service exceptions
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter disciplina", e);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<DisciplinaDTO> criarDisciplina(@RequestBody @Valid DisciplinaDTO disciplinaDTO) {
        try {
            DisciplinaDTO novaDisciplina = DisciplinaMapper.toDTO(disciplinaService.criarDisciplina(DisciplinaMapper.toEntity(disciplinaDTO))); // Changed to criarDisciplina
            return ResponseEntity.status(HttpStatus.CREATED).body(novaDisciplina); // Changed status to 201 Created
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e); // Handle duplicate code
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar disciplina", e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<DisciplinaDTO> atualizarDisciplina(@PathVariable Long id, @RequestBody @Valid DisciplinaDTO disciplinaDTO) { // Added @Valid
        try {
            var disciplinaAtualizada = disciplinaService.atualizarDisciplina(id, DisciplinaMapper.toEntity(disciplinaDTO));
            // The service now throws an exception if not found, so no null check needed here
            return ResponseEntity.ok(DisciplinaMapper.toDTO(disciplinaAtualizada));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e); // Handle not found or duplicate code
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar disciplina", e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable Long id) {
        try {
            boolean deletado = disciplinaService.deletarDisciplina(id);
            if (!deletado) {
                // This case should ideally be covered by a more specific exception from service,
                // but if not, this still handles it.
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrada para o ID fornecido.");
            }
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar disciplina", e);
        }
    }
}