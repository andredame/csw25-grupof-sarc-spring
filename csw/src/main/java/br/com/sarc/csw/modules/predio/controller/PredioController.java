package br.com.sarc.csw.modules.predio.controller;

import br.com.sarc.csw.modules.predio.dto.PredioDTO;
import br.com.sarc.csw.modules.predio.dto.PredioMapper;
import br.com.sarc.csw.modules.predio.model.Predio;
import br.com.sarc.csw.modules.predio.service.PredioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predios")
@RequiredArgsConstructor
public class PredioController {

    private final PredioService predioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<Predio>> listarTodos() {
        List<Predio> predios = predioService.listarTodos();
        return ResponseEntity.ok(predios);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<Predio> obterPorId(@PathVariable Long id) {
        Predio predio = predioService.obterPorId(id);
        return predio != null ? ResponseEntity.ok(predio) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<PredioDTO> criar(@Valid @RequestBody PredioDTO predioDTO) {
        // Converte o PredioDTO para a entidade Predio
        Predio predio = PredioMapper.toEntity(predioDTO);
        
        // Cria o novo prédio
        Predio novoPredio = predioService.criar(predio);
        
        // Retorna o Predio criado como DTO
        return ResponseEntity.ok(PredioMapper.toDTO(novoPredio));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Predio> atualizar(@PathVariable Long id, @Valid @RequestBody PredioDTO predioDTO) {
        // Converte o PredioDTO para a entidade Predio
        Predio predio = PredioMapper.toEntity(predioDTO);
        
        // Atualiza o predio
        Predio predioAtualizado = predioService.atualizar(id, predio);
        return predioAtualizado != null ? ResponseEntity.ok(predioAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = predioService.deletar(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}