package br.com.sarc.csw.modules.turma.dto;

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
@AllArgsConstructor
public class TurmaResponseDTO {
    private Long id;
    private String numero;
    private String semestre;
    private DisciplinaDTO disciplina; // Detalhes da disciplina
    private UUID professorId;
    private String horario;
    private Integer vagas;
    private List<AlunoResponseDTO> alunosIds;

   
    // Getters and Setters
    
}
