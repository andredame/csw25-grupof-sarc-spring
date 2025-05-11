package br.com.sarc.csw.modules.sala.dto;

import br.com.sarc.csw.modules.predio.dto.PredioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaResponseDTO {
    private Long id;
    private String nome;
    private Integer capacidade;
    private String andar;
    private PredioResumoDTO predio;
}
