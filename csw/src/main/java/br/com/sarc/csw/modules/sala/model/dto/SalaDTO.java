package br.com.sarc.csw.modules.sala.dto;

import br.com.sarc.csw.modules.predio.dto.PredioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaDTO {
    private Integer id;
    private String nome;
    private Integer capacidade;
    private String andar;
    private PredioDTO predio;
}