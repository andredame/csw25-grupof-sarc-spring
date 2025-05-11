package br.com.sarc.csw.modules.sala.dto;

import lombok.Data;

@Data
public class SalaRequestDTO {
    private String nome;
    private Integer capacidade;
    private String andar;
    private Long predioId; // só o ID do prédio para associação
}
