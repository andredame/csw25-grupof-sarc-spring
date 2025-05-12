package br.com.sarc.csw.modules.aula.dto;


import lombok.Data;

@Data
public class AulaRequestDTO {
    private String data; // Data da aula
    private Long turmaId; // ID da turma associada
    private Long salaId;  // ID da sala associada
    private Long disciplinaId; // ID da disciplina associada
    private String descricao;
}