package br.com.sarc.csw.modules.turma.dto;

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import br.com.sarc.csw.modules.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TurmaDTO {
    private Integer id;
    private String numero;
    private DisciplinaDTO disciplina;
    private String semestre;
    private UserDTO professor;
    private String horario;
    private Integer vagas;
    private List<UserDTO> alunos;
}