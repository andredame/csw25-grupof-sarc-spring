package br.com.sarc.csw.modules.sala.controller;

import br.com.sarc.csw.modules.sala.dto.SalaRequestDTO;
import br.com.sarc.csw.modules.sala.dto.SalaResponseDTO;
import br.com.sarc.csw.modules.sala.dto.SalaMapper;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.sala.service.SalaService;
import br.com.sarc.csw.modules.predio.service.PredioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/predios/{predioId}/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    
    @PostMapping
    public ResponseEntity<SalaResponseDTO> adicionarSala(@PathVariable Long predioId, @RequestBody SalaRequestDTO salaDTO) {
        Sala sala = SalaMapper.toEntity(salaDTO);
        Sala novaSala = salaService.adicionarSala(predioId, sala);
        return ResponseEntity.ok(SalaMapper.toResponseDTO(novaSala));
    }

    @PutMapping("/{salaId}")
    public ResponseEntity<SalaResponseDTO> editarSala(@PathVariable Long predioId, @PathVariable Long salaId, @RequestBody SalaRequestDTO salaDTO) {
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
}
