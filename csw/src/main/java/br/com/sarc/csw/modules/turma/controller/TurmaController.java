package br.com.sarc.csw.modules.turma.controller;

import br.com.sarc.csw.modules.turma.dto.TurmaDTO;
import br.com.sarc.csw.modules.turma.dto.TurmaMapper;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.service.TurmaService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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

    @Autowired
    private  TurmaService turmaService;

    // Listar todas as turmas de um professor
    @GetMapping("/professor/{professorId}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<TurmaDTO>> listarTurmasPorProfessor(@PathVariable UUID professorId) {
        List<Turma> turmas = turmaService.listarTurmasPorProfessor(professorId);
        List<TurmaDTO> turmasDTO = turmas.stream()
                .map(TurmaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(turmasDTO);
    }

    // Obter detalhes de uma turma específica
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<TurmaDTO> obterTurma(@PathVariable Long id) {
        Turma turma = turmaService.obterPorId(id);
        return turma != null ? ResponseEntity.ok(TurmaMapper.toDTO(turma)) : ResponseEntity.notFound().build();
    }

    // Atualizar uma turma (somente coordenadores)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable Long id, @RequestBody TurmaDTO turmaDTO) {
        Turma turmaAtualizada = turmaService.atualizarTurma(id, TurmaMapper.toEntity(turmaDTO));
        return turmaAtualizada != null ? ResponseEntity.ok(TurmaMapper.toDTO(turmaAtualizada)) : ResponseEntity.notFound().build();
    }
}