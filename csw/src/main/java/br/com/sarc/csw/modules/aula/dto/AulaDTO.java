package br.com.sarc.csw.modules.aula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AulaDTO {

    private Long id;
    private String data;
    private Long turmaId;   // Apenas o ID da turma
    private Long salaId;    // Apenas o ID da sala
}