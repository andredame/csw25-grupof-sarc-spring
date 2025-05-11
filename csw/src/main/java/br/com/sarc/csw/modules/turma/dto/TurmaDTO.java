package br.com.sarc.csw.modules.turma.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TurmaDTO {
    private Long id;
    private String numero;
    private String semestre;
    private Long disciplinaId;
    private UUID professorId;
    private String horario;
    private Integer vagas;
    private List<UUID> alunosIds;
}
