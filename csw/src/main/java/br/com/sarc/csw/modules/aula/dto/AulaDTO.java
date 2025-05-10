package br.com.sarc.csw.modules.aula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AulaDTO {

    private Integer id;
    private String data;
    private Integer turmaId;   // Apenas o ID da turma
    private Integer salaId;    // Apenas o ID da sala
}