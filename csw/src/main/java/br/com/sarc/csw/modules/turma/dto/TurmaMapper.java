package br.com.sarc.csw.modules.turma.dto;

import java.util.stream.Collectors;

import br.com.sarc.csw.modules.disciplina.dto.DisciplinaMapper;
import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import br.com.sarc.csw.modules.user.model.User;

public class TurmaMapper {

    public static TurmaDTO toDTO(Turma turma) {
        TurmaDTO dto = new TurmaDTO();
        dto.setId(turma.getId());
        dto.setNumero(turma.getNumero());
        dto.setSemestre(turma.getSemestre());
        dto.setHorario(turma.getHorario());
        dto.setVagas(turma.getVagas());

        dto.setDisciplina(turma.getDisciplina() != null ? DisciplinaMapper.toDTO(turma.getDisciplina()) : null);


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
        if (dto == null) return null; // Adicione esta verificação de nullidade para o DTO de entrada

        Turma turma = new Turma();
        turma.setId(dto.getId());
        turma.setNumero(dto.getNumero());
        turma.setSemestre(dto.getSemestre());
        turma.setHorario(dto.getHorario());
        turma.setVagas(dto.getVagas());

        // CORREÇÃO AQUI: Acessar o ID da disciplina através do objeto DisciplinaDTO
        if (dto.getDisciplina() != null) { // Verifique se o objeto DisciplinaDTO não é null
            Disciplina disciplina = new Disciplina();
            disciplina.setId(dto.getDisciplina().getId()); // O ID está dentro do DisciplinaDTO
            turma.setDisciplina(disciplina);
        }
        // Se TurmaDTO ainda tiver disciplinaId (para casos de request),
        // você precisaria decidir qual usar ou ter DTOs separados para request/response.
        // Assumindo que para toEntity, você quer usar o ID do objeto DisciplinaDTO.

        if (dto.getProfessorId() != null) { // Verifique se o professorId não é null
            User professor = new User();
            professor.setId(dto.getProfessorId());
            turma.setProfessor(professor);
        }


        turma.setAlunos(
            dto.getAlunos() != null // Certifique-se de que o campo alunos exista no TurmaDTO
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