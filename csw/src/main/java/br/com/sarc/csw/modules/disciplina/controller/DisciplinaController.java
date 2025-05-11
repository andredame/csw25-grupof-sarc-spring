package br.com.sarc.csw.modules.disciplina.controller;

import java.util.List;

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
        DisciplinaDTO disciplina = DisciplinaMapper.toDTO(disciplinaService.getDisciplina(id).orElse(null));
        return disciplina != null ? ResponseEntity.ok(disciplina) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<DisciplinaDTO> criarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO novaDisciplina = DisciplinaMapper.toDTO(disciplinaService.salvarDisciplina(DisciplinaMapper.toEntity(disciplinaDTO)));
        return ResponseEntity.ok(novaDisciplina);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")

    public ResponseEntity<DisciplinaDTO> atualizarDisciplina(@PathVariable Long id, @RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO disciplinaAtualizada = DisciplinaMapper.toDTO(disciplinaService.atualizarDisciplina(id, DisciplinaMapper.toEntity(disciplinaDTO)));
        return disciplinaAtualizada != null ? ResponseEntity.ok(disciplinaAtualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable Long id) {
        boolean deletado = disciplinaService.deletarDisciplina(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}