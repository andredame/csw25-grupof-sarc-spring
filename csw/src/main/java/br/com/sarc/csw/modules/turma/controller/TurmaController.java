package br.com.sarc.csw.modules.turma.controller;

import br.com.sarc.csw.modules.turma.dto.TurmaDTO;
import br.com.sarc.csw.modules.turma.dto.TurmaMapper;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.service.TurmaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService turmaService;

    @GetMapping("/professor/{professorId}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<TurmaDTO>> listarTurmasPorProfessor(@PathVariable UUID professorId) {
        List<Turma> turmas = turmaService.listarTurmasPorProfessor(professorId);
        if (turmas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma turma encontrada para o professor fornecido.");
        }
        List<TurmaDTO> turmasDTO = turmas.stream()
                .map(TurmaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(turmasDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<TurmaDTO>> listarTodasTurmas() {
        List<Turma> turmas = turmaService.listarTodasTurmas();
        if (turmas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma turma encontrada.");
        }
        List<TurmaDTO> turmasDTO = turmas.stream()
                .map(TurmaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(turmasDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<TurmaDTO> obterTurma(@PathVariable Long id) {
        Turma turma = turmaService.obterPorId(id);
        if (turma == null) {
            throw new IllegalArgumentException("Turma não encontrada para o ID fornecido.");
        }
        return ResponseEntity.ok(TurmaMapper.toDTO(turma));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO) {
        Turma turmaAtualizada = turmaService.atualizarTurma(id, TurmaMapper.toEntity(turmaDTO));
        if (turmaAtualizada == null) {
            throw new IllegalArgumentException("Turma não encontrada para o ID fornecido.");
        }
        return ResponseEntity.ok(TurmaMapper.toDTO(turmaAtualizada));
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaDTO> criarTurma(@RequestBody TurmaDTO turmaDTO) {
        Turma novaTurma = turmaService.criarTurma(TurmaMapper.toEntity(turmaDTO));
        return ResponseEntity.ok(TurmaMapper.toDTO(novaTurma));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }
}