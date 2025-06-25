package br.com.sarc.csw.modules.turma.dto;

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import br.com.sarc.csw.modules.user.dto.UserResponseDto; // NOVO: Importe UserResponseDTO
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // NOVO: Importe NoArgsConstructor
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor // NOVO: Adicionado para boa prática de DTOs e frameworks
public class TurmaResponseDTO {
    private Long id;
    private String numero;
    private String semestre;
    private DisciplinaDTO disciplina; // Detalhes da disciplina

    private UserResponseDto professor; // ALTERADO: Agora é o objeto completo do professor, não apenas o ID

    private String horario;
    private Integer vagas;
    private List<AlunoResponseDTO> alunos; // ALTERADO: Nome do campo de 'alunosIds' para 'alunos'

    // Os getters and setters serão gerados pelo Lombok (@Data)
}