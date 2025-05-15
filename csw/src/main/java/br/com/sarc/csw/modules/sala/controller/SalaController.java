package br.com.sarc.csw.modules.sala.controller;

import br.com.sarc.csw.modules.sala.dto.SalaRequestDTO;
import br.com.sarc.csw.modules.sala.dto.SalaResponseDTO;
import br.com.sarc.csw.modules.sala.dto.SalaMapper;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.sala.service.SalaService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/predios/{predioId}/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    public ResponseEntity<SalaResponseDTO> adicionarSala(@PathVariable Long predioId,@Valid @RequestBody SalaRequestDTO salaDTO) {
        Sala sala = SalaMapper.toEntity(salaDTO);
        Sala novaSala = salaService.adicionarSala(predioId, sala);
        return ResponseEntity.ok(SalaMapper.toResponseDTO(novaSala));
    }

    @PutMapping("/{salaId}")
    public ResponseEntity<SalaResponseDTO> editarSala(@PathVariable Long predioId, @PathVariable Long salaId, @Valid @RequestBody SalaRequestDTO salaDTO) {
        Sala sala = SalaMapper.toEntity(salaDTO);
        Sala salaAtualizada = salaService.editarSala(predioId, salaId, sala);
        return salaAtualizada != null
                ? ResponseEntity.ok(SalaMapper.toResponseDTO(salaAtualizada))
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listarSalas(@PathVariable Long predioId) {
        List<Sala> salas = salaService.listarSalasPorPredio(predioId);
        List<SalaResponseDTO> salasDTO = salas.stream()
                .map(SalaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(salasDTO);
    }

    @GetMapping("/{salaId}")
    public ResponseEntity<SalaResponseDTO> buscarSalaPorId(@PathVariable Long predioId, @PathVariable Long salaId) {
        Sala sala = salaService.buscarSalaPorId(predioId, salaId);
        return sala != null
                ? ResponseEntity.ok(SalaMapper.toResponseDTO(sala))
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{salaId}")
    public ResponseEntity<Void> deletarSala(@PathVariable Long predioId, @PathVariable Long salaId) {
        boolean removida = salaService.deletarSala(predioId, salaId);
        return removida
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<SalaResponseDTO>> filtrarSalasPorCapacidade(
            @PathVariable Long predioId,
            @RequestParam int capacidadeMinima
    ) {
        List<Sala> salas = salaService.filtrarPorCapacidade(predioId, capacidadeMinima);
        List<SalaResponseDTO> salasDTO = salas.stream()
                .map(SalaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(salasDTO);
    }
}
