package br.com.sarc.csw.modules.turma.dto;

import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import br.com.sarc.csw.modules.user.dto.UserMapper;
import br.com.sarc.csw.modules.disciplina.dto.DisciplinaMapper;
import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.user.model.User;

import java.util.stream.Collectors;

public class TurmaMapper {

    public static TurmaDTO toDTO(Turma turma) {
        TurmaDTO dto = new TurmaDTO();
        dto.setId(turma.getId());
        dto.setNumero(turma.getNumero());
        dto.setSemestre(turma.getSemestre());
        dto.setHorario(turma.getHorario());
        dto.setVagas(turma.getVagas());

        if (turma.getDisciplina() != null) {
            dto.setDisciplinaId(turma.getDisciplina().getId());
        }

        if (turma.getProfessor() != null) {
            dto.setProfessorId(turma.getProfessor().getId());
        }

        dto.setAlunos(
        turma.getAlunos() != null
            ? turma.getAlunos().stream()
                .map(aluno -> new AlunoResponseDTO(aluno.getId(), aluno.getUsername(), aluno.getEmail()))
                .collect(Collectors.toList())
            : null
        );

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
        dto.getAlunos() != null
            ? dto.getAlunos().stream().map(alunoDTO -> {
                User aluno = new User();
                aluno.setId(alunoDTO.getId());
                aluno.setUsername(alunoDTO.getUsername());
                aluno.setEmail(alunoDTO.getEmail());
                return aluno;
            }).collect(Collectors.toList())
            : null
    );

        return turma;
    }

    public static TurmaResponseDTO toResponseDTO(Turma turma) {
        return new TurmaResponseDTO(
            turma.getId(),
            turma.getNumero(),
            turma.getSemestre(),
            turma.getDisciplina() != null ? DisciplinaMapper.toDTO(turma.getDisciplina()) : null,
            turma.getProfessor() != null ? turma.getProfessor().getId() : null,
            turma.getHorario(),
            turma.getVagas(),
            turma.getAlunos() != null
                ? turma.getAlunos().stream()
                    .map(aluno -> new AlunoResponseDTO(aluno.getId(),aluno.getUsername(),aluno.getEmail())) // Inclui ID e nome
                    .collect(Collectors.toList())
                : null
        );
    }
}