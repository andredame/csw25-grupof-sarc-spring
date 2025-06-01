package br.com.sarc.csw.modules.turma.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<TurmaResponseDTO>> listarTurmasPorProfessor(@PathVariable UUID professorId) {
        List<Turma> turmas = turmaService.listarTurmasPorProfessor(professorId);
        if (turmas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma turma encontrada para o professor fornecido.");
        }
        List<TurmaResponseDTO> turmasResponseDTO = turmas.stream()
                .map(TurmaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(turmasResponseDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('COORDENADOR')")
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
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable Long id, @RequestBody @Valid TurmaDTO turmaDTO) {
        Turma turmaAtualizada = turmaService.atualizarTurma(id, TurmaMapper.toEntity(turmaDTO));
        if (turmaAtualizada == null) {
            throw new IllegalArgumentException("Turma não encontrada para o ID fornecido.");
        }
        return ResponseEntity.ok(TurmaMapper.toDTO(turmaAtualizada));
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaDTO> criarTurma(@RequestBody @Valid TurmaDTO turmaDTO) {
        Turma novaTurma = turmaService.criarTurma(TurmaMapper.toEntity(turmaDTO));
        return ResponseEntity.ok(TurmaMapper.toDTO(novaTurma));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/alunos")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<AlunoResponseDTO>> listarAlunosPorTurma(@PathVariable Long id) {
        List<User> alunos = turmaService.listarAlunosPorTurma(id);
        List<AlunoResponseDTO> alunosDTO = alunos.stream()
                .map(UserMapper::toAlunoResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alunosDTO);
    }

    @PostMapping("/{turmaId}/alunos/{alunoId}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<AlunoResponseDTO> vincularAlunoATurma(@PathVariable Long turmaId, @PathVariable UUID alunoId) {
        User alunoVinculado = turmaService.vincularAlunoATurma(turmaId, alunoId);
        
        if (alunoVinculado == null) {
            return ResponseEntity.notFound().build();
        }

        AlunoResponseDTO responseDTO = UserMapper.toAlunoResponseDTO(alunoVinculado);
        return ResponseEntity.ok(responseDTO);
    }
    //vincular professor a turma
    @PostMapping("/{turmaId}/professores/{professorId}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<TurmaResponseDTO> vincularProfessorATurma(@PathVariable Long turmaId, @PathVariable UUID professorId) {
        Turma turmaVinculada = turmaService.vincularProfessorATurma(turmaId, professorId);

        if (turmaVinculada == null) {
            return ResponseEntity.notFound().build();
        }

        TurmaResponseDTO responseDTO = TurmaMapper.toResponseDTO(turmaVinculada);
        return ResponseEntity.ok(responseDTO);
    }

}