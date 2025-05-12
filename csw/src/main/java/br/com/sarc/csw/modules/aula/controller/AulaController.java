package br.com.sarc.csw.modules.aula.controller;

import br.com.sarc.csw.modules.aula.dto.AulaRequestDTO;
import br.com.sarc.csw.modules.aula.dto.AulaResponseDTO;
import br.com.sarc.csw.modules.aula.dto.AulaMapper;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.aula.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    // Endpoint para criar uma nova aula
    @PostMapping
    public ResponseEntity<AulaResponseDTO> criarAula(@RequestBody AulaRequestDTO aulaRequestDTO) {
        Aula aula = AulaMapper.toEntity(aulaRequestDTO);
        Aula aulaCriada = aulaService.salvar(aula);
        AulaResponseDTO responseDTO = AulaMapper.toResponseDTO(aulaCriada);
        return ResponseEntity.ok(responseDTO);
    }

    // Endpoint para buscar todas as aulas
    @GetMapping
    public ResponseEntity<List<AulaResponseDTO>> listarAulas() {
        List<Aula> aulas = aulaService.buscarTodas();
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    // Endpoint para buscar uma aula por ID
    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> buscarAulaPorId(@PathVariable Long id) {
        Aula aula = aulaService.buscarPorId(id);
        if (aula == null) {
            return ResponseEntity.notFound().build();
        }
        AulaResponseDTO responseDTO = AulaMapper.toResponseDTO(aula);
        return ResponseEntity.ok(responseDTO);
    }

    // Endpoint para atualizar uma aula
    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> atualizarAula(
            @PathVariable Long id,
            @RequestBody AulaRequestDTO aulaRequestDTO) {
        Aula aulaExistente = aulaService.buscarPorId(id);
        if (aulaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Aula aulaAtualizada = AulaMapper.toEntity(aulaRequestDTO);
        aulaAtualizada.setId(id); // Garante que o ID seja mantido
        Aula aulaSalva = aulaService.salvar(aulaAtualizada);
        AulaResponseDTO responseDTO = AulaMapper.toResponseDTO(aulaSalva);
        return ResponseEntity.ok(responseDTO);
    }

    // Endpoint para deletar uma aula
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAula(@PathVariable Long id) {
        Aula aulaExistente = aulaService.buscarPorId(id);
        if (aulaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        aulaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}