package br.com.sarc.csw.modules.disciplina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisciplinaDTO {
    private Integer id;
    private String codigo;
    private String nome;
    private Integer creditos;
    private String semestre;
    private String programa;
}