package br.com.sarc.csw.modules.recurso.controller;

import br.com.sarc.csw.modules.recurso.dto.RecursoDTO;
import br.com.sarc.csw.modules.recurso.dto.RecursoMapper;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.service.RecursoService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recursos")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoService recursoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecursoDTO> criarRecurso(@RequestBody RecursoDTO recursoDTO) {
        Recurso novoRecurso = recursoService.criar(RecursoMapper.toEntity(recursoDTO));
        return ResponseEntity.status(201).body(RecursoMapper.toDTO(novoRecurso));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<RecursoDTO>> listarRecursos() {
        List<Recurso> recursos = recursoService.listarTodos();
        List<RecursoDTO> recursosDTO = recursos.stream()
                .map(RecursoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recursosDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<RecursoDTO> obterRecurso(@PathVariable Long id) {
        Recurso recurso = recursoService.obterPorId(id);
        if (recurso == null) {
            throw new IllegalArgumentException("Recurso não encontrado para o ID fornecido.");
        }
        return ResponseEntity.ok(RecursoMapper.toDTO(recurso));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecursoDTO> atualizarRecurso(@PathVariable Long id, @RequestBody RecursoDTO recursoDTO) {
        Recurso recursoAtualizado = recursoService.atualizar(id, RecursoMapper.toEntity(recursoDTO));
        if (recursoAtualizado == null) {
            throw new IllegalArgumentException("Recurso não encontrado para o ID fornecido.");
        }
        return ResponseEntity.ok(RecursoMapper.toDTO(recursoAtualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarRecurso(@PathVariable Long id) {
        boolean deletado = recursoService.deletar(id);
        if (!deletado) {
            throw new IllegalArgumentException("Recurso não encontrado para o ID fornecido.");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/disponibilidade")
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('COORDENADOR')")
    public ResponseEntity<Boolean> verificarDisponibilidade(@PathVariable Long id) {
        boolean disponivel = recursoService.verificarDisponibilidade(id);
        return ResponseEntity.ok(disponivel);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id, @RequestParam String status) {
        boolean atualizado = recursoService.alterarStatus(id, status);
        if (!atualizado) {
            throw new IllegalArgumentException("Recurso não encontrado ou status inválido.");
        }
        return ResponseEntity.noContent().build();
    }
}