package br.com.sarc.csw.modules.recurso.dto;

import br.com.sarc.csw.core.enums.StatusRecurso;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecursoDTO {

    private Long id;

    @NotNull(message = "O status do recurso é obrigatório")
    private StatusRecurso status;

    @NotNull(message = "O tipo de recurso é obrigatório")
    private TipoRecursoDTO tipoRecurso;
}
