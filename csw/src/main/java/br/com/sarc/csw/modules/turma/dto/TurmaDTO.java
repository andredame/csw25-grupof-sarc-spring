package br.com.sarc.csw.modules.turma.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import br.com.sarc.csw.modules.user.dto.UserResponseDto; // NOVO: Importe UserResponseDTO

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class TurmaDTO {
    private Long id; // Para retorno ou atualização por ID

    @NotBlank(message = "O número da turma é obrigatório")
    @Schema(description = "Número da turma", example = "T001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numero;

    @NotBlank(message = "O semestre é obrigatório")
    @Schema(description = "Semestre letivo da turma", example = "2025/1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String semestre;

    @NotNull(message = "O ID da disciplina é obrigatório") // Validação para o ID de entrada
    @Schema(description = "ID da disciplina associada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long disciplinaId; // Campo para entrada de dados (ID)

    private DisciplinaDTO disciplina; // Campo para saída de dados (objeto completo)

    @NotNull(message = "O ID do professor é obrigatório") // Validação para o ID de entrada
    @Schema(description = "UUID do professor associado", example = "fe18a691-e7f0-4c5c-964f-273382a34598", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID professorId; // Campo para entrada de dados (ID)

    private UserResponseDto professor; // NOVO: Campo para saída de dados (objeto completo do professor)

    @NotBlank(message = "O horário é obrigatório")
    @Schema(description = "Horário da turma", example = "Segunda 08:00-10:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String horario;

    @NotNull(message = "O número de vagas é obrigatório")
    @Min(value = 1, message = "A turma deve ter pelo menos 1 vaga")
    @Schema(description = "Número de vagas disponíveis na turma", example = "30", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer vagas;

    private List<AlunoResponseDTO> alunos = Collections.emptyList(); // Valor padrão vazio

}