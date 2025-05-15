package br.com.sarc.csw.modules.aula.dto;

import br.com.sarc.csw.core.enums.PeriodoAula;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AulaRequestDTO {

    @NotBlank(message = "A data da aula é obrigatória")
    private String data;

    @NotNull(message = "O ID da turma é obrigatório")
    private Long turmaId;

    @NotNull(message = "O ID da sala é obrigatório")
    private Long salaId;

    @NotNull(message = "O ID da disciplina é obrigatório")
    private Long disciplinaId;

    @NotNull(message = "O período da aula é obrigatório")
    private PeriodoAula periodo;

    private String descricao;
}
