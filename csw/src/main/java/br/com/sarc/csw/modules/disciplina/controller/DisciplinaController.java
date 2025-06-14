package br.com.sarc.csw.modules.disciplina.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<List<DisciplinaDTO>> listarDisciplinas() {
        List<DisciplinaDTO> disciplinas = disciplinaService.listarDisciplinas()
                .stream()
                .map(DisciplinaMapper::toDTO)
                .toList();
        return ResponseEntity.ok(disciplinas);
    }
   

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<DisciplinaDTO> obterDisciplina(@PathVariable Long id) {
        return disciplinaService.getDisciplina(id)
                .map(disciplina -> ResponseEntity.ok(DisciplinaMapper.toDTO(disciplina)))
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada para o ID fornecido."));
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<DisciplinaDTO> criarDisciplina(@RequestBody @Valid DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO novaDisciplina = DisciplinaMapper.toDTO(disciplinaService.salvarDisciplina(DisciplinaMapper.toEntity(disciplinaDTO)));
        return ResponseEntity.ok(novaDisciplina);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<DisciplinaDTO> atualizarDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        var disciplinaAtualizada = disciplinaService.atualizarDisciplina(id, DisciplinaMapper.toEntity(disciplinaDTO));
        if (disciplinaAtualizada == null) {
            throw new IllegalArgumentException("Disciplina não encontrada para o ID fornecido.");
        }
        return ResponseEntity.ok(DisciplinaMapper.toDTO(disciplinaAtualizada));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable Long id) {
        boolean deletado = disciplinaService.deletarDisciplina(id);
        if (!deletado) {
            throw new IllegalArgumentException("Disciplina não encontrada para o ID fornecido.");
        }
        return ResponseEntity.noContent().build();
    }
}