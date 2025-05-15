package br.com.sarc.csw.modules.aula.dto;

import br.com.sarc.csw.core.enums.PeriodoAula;
import br.com.sarc.csw.modules.sala.dto.SalaResponseDTO;
import br.com.sarc.csw.modules.turma.dto.TurmaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AulaResponseDTO {
    private Long id; // ID da aula
    private String data; // Data da aula
    private String descricao; // Descrição da aula
    
    private TurmaDTO turma; // Detalhes da turma associada
    private SalaResponseDTO sala; // Detalhes da sala associada
    private PeriodoAula periodo;
}