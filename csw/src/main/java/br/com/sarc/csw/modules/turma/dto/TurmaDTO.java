package br.com.sarc.csw.modules.turma.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class TurmaDTO {
    private Long id;
    @NotBlank(message = "O número da turma é obrigatório")
    private String numero;

    @Schema(description = "ID do curso", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O semestre é obrigatório")
    private String semestre;

    private DisciplinaDTO disciplina; 

    @Schema(description = "UUID do professor", example = "fe18a691-e7f0-4c5c-964f-273382a34598", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID do professor é obrigatório")
    private UUID professorId;

    @Schema(description = "Data da aula", example = "2023-10-01", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O horário é obrigatório")
    private String horario;

    @Schema(description = "Número de vagas", example = "30", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O número de vagas é obrigatório")
    @Min(value = 1, message = "A turma deve ter pelo menos 1 vaga")
    private Integer vagas;

    @Schema(description = "ID da sala", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A lista de alunos não pode ser nula")
    private List<@NotNull(message = "ID de aluno inválido") AlunoResponseDTO> alunos;
}
