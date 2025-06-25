package br.com.sarc.csw.modules.recurso.controller;

import br.com.sarc.csw.modules.recurso.dto.RecursoDTO;
import br.com.sarc.csw.modules.recurso.dto.RecursoMapper;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.model.TipoRecurso;
import br.com.sarc.csw.modules.recurso.repository.TipoRecursoRepository;
import br.com.sarc.csw.modules.recurso.service.RecursoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoService recursoService;
    private final TipoRecursoRepository tipoRecursoRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<RecursoDTO>> listarRecursos() {
        List<Recurso> recursos = recursoService.listarTodos();
        List<RecursoDTO> recursosDTO = recursos.stream()
                .map(RecursoMapper::toDTO)
                .toList();
        return ResponseEntity.ok(recursosDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<RecursoDTO> obterRecurso(@PathVariable Long id) {
        Recurso recurso = recursoService.obterPorId(id);
        return recurso != null ? ResponseEntity.ok(RecursoMapper.toDTO(recurso)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<RecursoDTO> criarRecurso(@RequestBody @Valid RecursoDTO recursoDTO) {
        Recurso recurso = RecursoMapper.toEntity(recursoDTO);
        Recurso novoRecurso = recursoService.criar(recurso);
        return ResponseEntity.ok(RecursoMapper.toDTO(novoRecurso));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<RecursoDTO> atualizarRecurso(@PathVariable Long id, @RequestBody @Valid RecursoDTO recursoDTO) {
        Recurso recursoAtualizado = recursoService.atualizar(id, RecursoMapper.toEntity(recursoDTO));
        return recursoAtualizado != null ? ResponseEntity.ok(RecursoMapper.toDTO(recursoAtualizado)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletarRecurso(@PathVariable Long id) {
        boolean deletado = recursoService.deletar(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/tipos")
    public List<TipoRecurso> listarTodos() {
        return tipoRecursoRepository.findAll();
    }
}