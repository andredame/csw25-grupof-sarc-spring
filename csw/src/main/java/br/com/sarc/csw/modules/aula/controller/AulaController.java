package br.com.sarc.csw.modules.aula.controller;

import java.time.LocalDate; // Import LocalDate
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat; // Import for LocalDate in RequestParam
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.oauth2.jwt.Jwt; // Import Jwt for token details
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken; // Import JwtAuthenticationToken
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

import br.com.sarc.csw.core.exception.RecursoIndisponivelException;
import br.com.sarc.csw.modules.aula.dto.AulaMapper;
import br.com.sarc.csw.modules.aula.dto.AulaRequestDTO;
import br.com.sarc.csw.modules.aula.dto.AulaResponseDTO;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.aula.service.AulaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/aulas")
@SecurityRequirement(name = "bearerAuth")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    // Helper method to get professor ID from JWT
    private UUID getProfessorIdFromAuthentication(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            // Assuming the UUID is stored in a claim, e.g., "sub" (subject) or a custom claim like "userId"
            // You might need to adjust "sub" to the actual claim that holds the user ID in your Keycloak setup.
            String userIdString = jwt.getClaimAsString("sub");
            if (userIdString != null) {
                return UUID.fromString(userIdString);
            }
        }
        throw new IllegalStateException("Could not retrieve professor ID from authentication token.");
    }

    // Endpoint para criar uma nova aula
    @PostMapping
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<AulaResponseDTO> criarAula(@RequestBody @Valid AulaRequestDTO aulaRequestDTO,
                                                     Authentication authentication) {
        try {
            UUID professorId = getProfessorIdFromAuthentication(authentication);

            // Call the updated service method with individual parameters
            Aula aulaCriada = aulaService.criarAula(
                    aulaRequestDTO.getTurmaId(),
                    aulaRequestDTO.getSalaId(),
                    aulaRequestDTO.getData(),
                    aulaRequestDTO.getPeriodo(),
                    aulaRequestDTO.getDescricao(),
                    professorId
            );
            AulaResponseDTO responseDTO = AulaMapper.toResponseDTO(aulaCriada);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (RuntimeException e) {
            // Catch specific exceptions from the service and return appropriate HTTP status
            if (e.getMessage().contains("não encontrada") || e.getMessage().contains("não pertence")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Or a specific error DTO
            }
            if (e.getMessage().contains("já reservada") || e.getMessage().contains("capacidade")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Or a specific error DTO
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Generic error
        }
    }

    @GetMapping("/professor/{professorId}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<AulaResponseDTO>> listarAulasPorProfessor(@PathVariable UUID professorId, Authentication authentication) {
        // Optional: Add a check if the authenticated professor matches the requested professorId
        // UUID authenticatedProfessorId = getProfessorIdFromAuthentication(authentication);
        // if (!authenticatedProfessorId.equals(professorId)) {
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // }
        List<Aula> aulas = aulaService.listarAulasPorProfessor(professorId);
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    // Endpoint para buscar todas as aulas
    @GetMapping
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ALUNO')") 
    public ResponseEntity<List<AulaResponseDTO>> listarAulas() {
        List<Aula> aulas = aulaService.buscarTodas();
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/turma/{turmaId}") // Changed to use PathVariable for clarity
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ALUNO')") // Added ALUNO role
    public ResponseEntity<List<AulaResponseDTO>> listarAulasPorTurma(@PathVariable Long turmaId) {
        // Long turmaId = request.get("turmaId"); // No longer needed
        List<Aula> aulas = aulaService.listarAulasPorTurma(turmaId);
        List<AulaResponseDTO> responseDTOs = aulas.stream()
                .map(AulaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    // listar aulas por data
    @GetMapping("/data")
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ALUNO')") // Added ALUNO role
    public ResponseEntity<List<AulaResponseDTO>> listarAulasPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) { // Changed type to LocalDate
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
    @PreAuthorize("hasRole('PROFESSOR')") // Only professors can update
    public ResponseEntity<AulaResponseDTO> atualizarAula(
            @PathVariable Long id,
            @RequestBody @Valid AulaRequestDTO aulaRequestDTO,
            Authentication authentication) {
        try {
            // Optional: Verify if the professor attempting to update is the owner of the class or has permission
            // UUID professorId = getProfessorIdFromAuthentication(authentication);
            // Aula existingAula = aulaService.buscarPorId(id);
            // if (existingAula != null && !existingAula.getTurma().getProfessor().getId().equals(professorId)) {
            //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            // }

            // Create a temporary Aula object from DTO for update, let service handle fetching existing and merging
            Aula aulaToUpdate = AulaMapper.toEntity(aulaRequestDTO);

            Aula aulaSalva = aulaService.atualizarAula(id, aulaToUpdate);
            if (aulaSalva == null) {
                return ResponseEntity.notFound().build();
            }
            AulaResponseDTO responseDTO = AulaMapper.toResponseDTO(aulaSalva);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            // Handle exceptions from the service similarly to createAula
            if (e.getMessage().contains("não encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            if (e.getMessage().contains("já reservada") || e.getMessage().contains("capacidade")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para deletar uma aula
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')") // Only professors can delete
    public ResponseEntity<Void> deletarAula(@PathVariable Long id, Authentication authentication) {
        Aula aulaExistente = aulaService.buscarPorId(id);
        if (aulaExistente == null) {
            return ResponseEntity.notFound().build();
        }
        


        aulaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}