package br.com.sarc.csw.modules.turma.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus; // Import HttpStatus
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
import org.springframework.web.server.ResponseStatusException; // Import ResponseStatusException

import br.com.sarc.csw.modules.turma.dto.TurmaDTO;
import br.com.sarc.csw.modules.turma.dto.TurmaMapper;
import br.com.sarc.csw.modules.turma.dto.TurmaResponseDTO;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.service.TurmaService;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import br.com.sarc.csw.modules.user.dto.UserMapper;
import br.com.sarc.csw.modules.user.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TurmaController {

    private final TurmaService turmaService;

    @GetMapping("/professor/{professorId}")
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('COORDENADOR')") // Allow coordinator to list
    public ResponseEntity<List<TurmaResponseDTO>> listarTurmasPorProfessor(@PathVariable UUID professorId) {
        try {
            List<Turma> turmas = turmaService.listarTurmasPorProfessor(professorId);
            // No need to throw if empty, an empty list is a valid response
            List<TurmaResponseDTO> turmasResponseDTO = turmas.stream()
                    .map(TurmaMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(turmasResponseDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e); // Example if professor not found
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar turmas por professor", e);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PROFESSOR', 'COORDENADOR', 'ALUNO')") // Allow all relevant roles
    public ResponseEntity<List<TurmaResponseDTO>> listarTodasTurmas() {
        try {
            List<Turma> turmas = turmaService.listarTodasTurmas();
            // No need to throw if empty, an empty list is a valid response
            List<TurmaResponseDTO> turmasDTO = turmas.stream()
                    .map(TurmaMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(turmasDTO);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar todas as turmas", e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFESSOR', 'COORDENADOR', 'ALUNO')") // Allow all relevant roles
    public ResponseEntity<TurmaDTO> obterTurma(@PathVariable Long id) {
        try {
            Turma turma = turmaService.obterPorId(id);
            if (turma == null) {
                // If service returns null, throw NOT_FOUND
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada para o ID fornecido.");
            }
            return ResponseEntity.ok(TurmaMapper.toDTO(turma));
        } catch (IllegalArgumentException e) { // Catch exceptions from service (e.g., if ID format is wrong)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao obter turma", e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable Long id, @RequestBody @Valid TurmaDTO turmaDTO) {
        try {
            // Service now throws IllegalArgumentException if not found, so no null check here
            Turma turmaAtualizada = turmaService.atualizarTurma(id, TurmaMapper.toEntity(turmaDTO));
            return ResponseEntity.ok(TurmaMapper.toDTO(turmaAtualizada));
        } catch (IllegalArgumentException e) { // Catch not found or validation errors from service
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar turma", e);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaDTO> criarTurma(@RequestBody @Valid TurmaDTO turmaDTO) {
        try {
            Turma novaTurma = turmaService.criarTurma(TurmaMapper.toEntity(turmaDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(TurmaMapper.toDTO(novaTurma)); // Changed to 201 Created
        } catch (IllegalArgumentException e) { // Catch validation errors (e.g., disciplina/professor not found)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar turma", e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        try {
            // Service now throws IllegalArgumentException if not found
            turmaService.deletarTurma(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e); // Use NOT_FOUND for "not found"
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao deletar turma", e);
        }
    }

    @GetMapping("/{id}/alunos")
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ALUNO', 'COORDENADOR')") // Allow students to see their classmates (if in same turma)
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosPorTurma(@PathVariable Long id) {
        try {
            List<User> alunos = turmaService.listarAlunosPorTurma(id);
            List<AlunoResponseDTO> alunosDTO = alunos.stream()
                    .map(UserMapper::toAlunoResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(alunosDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar alunos da turma", e);
        }
    }

    @PutMapping("/{turmaId}/alunos/{alunoId}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<AlunoResponseDTO> vincularAlunoATurma(
        @PathVariable Long turmaId, 
        @PathVariable UUID alunoId) {
        
        try {
            User alunoVinculado = turmaService.vincularAlunoATurma(turmaId, alunoId);
            return ResponseEntity.ok(UserMapper.toAlunoResponseDTO(alunoVinculado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AlunoResponseDTO(null, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(new AlunoResponseDTO(null, "Erro interno: " + e.getMessage(), null));
        }
    }

    @PostMapping("/{turmaId}/professores/{professorId}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaResponseDTO> vincularProfessorATurma(@PathVariable Long turmaId, @PathVariable UUID professorId) {
        try {
            // Service now throws exceptions for not found or duplicate professor
            Turma turmaVinculada = turmaService.vincularProfessorATurma(turmaId, professorId);
            TurmaResponseDTO responseDTO = TurmaMapper.toResponseDTO(turmaVinculada);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO); // Changed to OK or CREATED if preferred
        } catch (IllegalArgumentException e) { // Catch specific business logic exceptions
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao vincular professor à turma", e);
        }
    }

    @DeleteMapping("/{turmaId}/alunos/{alunoId}")
    @PreAuthorize("hasRole('COORDENADOR')") // Apenas COORDENADOR pode remover aluno
    public ResponseEntity<Void> removerAlunoDeTurma(
            @PathVariable Long turmaId,
            @PathVariable UUID alunoId) {
        try {
            turmaService.removerAlunoDeTurma(turmaId, alunoId);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se sucesso
        } catch (IllegalArgumentException e) {
            // Se turma ou aluno não encontrados, ou aluno não está na turma
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao remover aluno da turma", e);
        }
    }
}