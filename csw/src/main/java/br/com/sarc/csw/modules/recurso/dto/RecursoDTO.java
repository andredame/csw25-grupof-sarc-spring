package br.com.sarc.csw.modules.recurso.dto;

import br.com.sarc.csw.core.enums.StatusRecurso;
import br.com.sarc.csw.modules.recurso.model.TipoRecurso;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecursoDTO {
    private Long id;
    private StatusRecurso status; 
    private TipoRecursoDTO tipoRecurso;

    
}
