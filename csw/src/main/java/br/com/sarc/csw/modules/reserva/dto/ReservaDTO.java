package br.com.sarc.csw.modules.reserva.dto;

import br.com.sarc.csw.modules.aula.dto.AulaDTO;
import br.com.sarc.csw.modules.recurso.dto.RecursoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Integer id;
    private Integer id_recurso;
    private Integer id_aula;
}
