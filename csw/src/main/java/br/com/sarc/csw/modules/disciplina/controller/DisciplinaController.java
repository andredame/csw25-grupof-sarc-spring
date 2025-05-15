package br.com.sarc.csw.modules.disciplina.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import br.com.sarc.csw.modules.disciplina.dto.DisciplinaMapper;
import br.com.sarc.csw.modules.disciplina.service.DisciplinaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @GetMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<DisciplinaDTO>> listarDisciplinas() {
        List<DisciplinaDTO> disciplinas = disciplinaService.listarDisciplinas()
                .stream()
                .map(DisciplinaMapper::toDTO)
                .toList();
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<DisciplinaDTO> obterDisciplina(@PathVariable Long id) {
        return disciplinaService.getDisciplina(id)
                .map(disciplina -> ResponseEntity.ok(DisciplinaMapper.toDTO(disciplina)))
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada para o ID fornecido."));
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<DisciplinaDTO> criarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO novaDisciplina = DisciplinaMapper.toDTO(
                disciplinaService.salvarDisciplina(DisciplinaMapper.toEntity(disciplinaDTO)));
        return ResponseEntity.ok(novaDisciplina);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<DisciplinaDTO> atualizarDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        var disciplinaAtualizada = disciplinaService.atualizarDisciplina(id, DisciplinaMapper.toEntity(disciplinaDTO));
        if (disciplinaAtualizada == null) {
            throw new IllegalArgumentException("Disciplina não encontrada para o ID fornecido.");
        }
        return ResponseEntity.ok(DisciplinaMapper.toDTO(disciplinaAtualizada));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable Long id) {
        boolean deletado = disciplinaService.deletarDisciplina(id);
        if (!deletado) {
            throw new IllegalArgumentException("Disciplina não encontrada para o ID fornecido.");
        }
        return ResponseEntity.noContent().build();
    }
}