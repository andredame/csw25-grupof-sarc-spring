package br.com.sarc.csw.modules.aula.dto;

import br.com.sarc.csw.core.enums.PeriodoAula;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AulaRequestDTO {
    @Schema(description = "Data da aula", example = "2023-10-01", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A data da aula é obrigatória")
    private String data;

    @Schema(description = "ID da turma", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da turma é obrigatório")
    private Long turmaId;

    @Schema(description = "ID da sala", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da sala é obrigatório")
    private Long salaId;

    @Schema(description = "ID da disciplina", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da disciplina é obrigatório")
    private Long disciplinaId;

    @Schema(description = "Período da aula", example = "JK", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O período da aula é obrigatório")
    private PeriodoAula periodo;

    private String descricao;
}
