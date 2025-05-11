package br.com.sarc.csw.modules.turma.dto;

import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.disciplina.model.Disciplina;

import java.util.UUID;
import java.util.stream.Collectors;

public class TurmaMapper {

    public static TurmaDTO toDTO(Turma turma) {
        TurmaDTO dto = new TurmaDTO();
        dto.setId(turma.getId());
        dto.setNumero(turma.getNumero());
        dto.setSemestre(turma.getSemestre());
        dto.setHorario(turma.getHorario());
        dto.setVagas(turma.getVagas());
        dto.setDisciplinaId(turma.getDisciplina().getId());
        dto.setProfessorId(turma.getProfessor().getId());
        dto.setAlunosIds(turma.getAlunos().stream().map(User::getId).collect(Collectors.toList()));
        return dto;
    }

    public static Turma toEntity(TurmaDTO dto) {
        Turma turma = new Turma();
        turma.setId(dto.getId());
        turma.setNumero(dto.getNumero());
        turma.setSemestre(dto.getSemestre());
        turma.setHorario(dto.getHorario());
        turma.setVagas(dto.getVagas());

        Disciplina disciplina = new Disciplina();
        disciplina.setId(dto.getDisciplinaId());
        turma.setDisciplina(disciplina);

        User professor = new User();
        professor.setId(dto.getProfessorId());
        turma.setProfessor(professor);

        turma.setAlunos(
            dto.getAlunosIds() != null ?
            dto.getAlunosIds().stream().map(id -> {
                User aluno = new User();
                aluno.setId(id);
                return aluno;
            }).collect(Collectors.toList()) : null
        );

        return turma;
    }
}
