package br.com.sarc.csw.modules.reserva.dto;
import br.com.sarc.csw.modules.aula.dto.AulaResponseDTO;
import br.com.sarc.csw.modules.recurso.dto.RecursoDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservaResponseDTO {
    private Long id;
    private AulaResponseDTO aula;
    private RecursoDTO recurso;
}