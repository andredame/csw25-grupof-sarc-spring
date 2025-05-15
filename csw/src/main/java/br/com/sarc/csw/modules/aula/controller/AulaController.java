package br.com.sarc.csw.modules.aula.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sarc.csw.modules.aula.dto.AulaMapper;
import br.com.sarc.csw.modules.aula.dto.AulaRequestDTO;
import br.com.sarc.csw.modules.aula.dto.AulaResponseDTO;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.aula.service.AulaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/aulas")

public class AulaController {

    @Autowired
    private AulaService aulaService;

    // Endpoint para criar uma nova aula
    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<AulaResponseDTO> criarAula(@RequestBody @Valid AulaRequestDTO aulaRequestDTO) {
        Aula aula = AulaMapper.toEntity(aulaRequestDTO);
        Aula aulaCriada = aulaService.salvar(aula);
        AulaResponseDTO responseDTO = AulaMapper.toResponseDTO(aulaCriada);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/professor/{professorId}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<AulaResponseDTO>> listarAulasPorProfessor(@PathVariable UUID professorId) {
        List<Aula> aulas = aulaService.listarAulasPorProfessor(professorId);
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    // Endpoint para buscar todas as aulas
    @GetMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<AulaResponseDTO>> listarAulas() {
        List<Aula> aulas = aulaService.buscarTodas();
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    @GetMapping("/turma")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<AulaResponseDTO>> listarAulasPorTurma(@RequestBody Map<String, Long> request) {
        Long turmaId = request.get("turmaId");
        List<Aula> aulas = aulaService.listarAulasPorTurma(turmaId);
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    
    //listar aulas por data 
    @GetMapping("/data")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<AulaResponseDTO>> listarAulasPorData(@RequestParam String data) {
        List<Aula> aulas = aulaService.listarAulasPorData(data);
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> buscarAulaPorId(@PathVariable Long id) {
        Aula aula = aulaService.buscarPorId(id);
        if (aula == null) {
            return ResponseEntity.notFound().build();
        }
        AulaResponseDTO responseDTO = AulaMapper.toResponseDTO(aula);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> atualizarAula(
            @PathVariable Long id,
            @RequestBody @Valid AulaRequestDTO aulaRequestDTO) {
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