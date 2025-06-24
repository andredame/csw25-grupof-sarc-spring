package br.com.sarc.csw.modules.aula.dto;

import br.com.sarc.csw.core.enums.PeriodoAula;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull; // Changed from NotBlank
import lombok.Data;

import java.time.LocalDate; // Added import for LocalDate

@Data
public class AulaRequestDTO {
    @Schema(description = "Data da aula", example = "2023-10-01", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A data da aula é obrigatória") // Changed validation
    private LocalDate data; // Changed type to LocalDate

    @Schema(description = "ID da turma", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da turma é obrigatório")
    private Long turmaId;

    @Schema(description = "ID da sala", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O ID da sala é obrigatório")
    private Long salaId;


    @Schema(description = "Período da aula", example = "JK", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O período da aula é obrigatório")
    private PeriodoAula periodo;

    private String descricao;
}