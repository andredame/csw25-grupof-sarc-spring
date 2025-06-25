package br.com.sarc.csw.modules.turma.dto;

import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import br.com.sarc.csw.modules.user.dto.UserMapper;
import br.com.sarc.csw.modules.disciplina.dto.DisciplinaMapper;
import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.dto.UserResponseDto; // CORRIGIDO: Importe UserResponseDTO (com 'DTO' maiúsculo)

import java.util.stream.Collectors;

public class TurmaMapper {

    public static TurmaDTO toDTO(Turma turma) { // Usado pelo AulaMapper e TurmaController para listar turmas com detalhes
        if (turma == null) return null;

        TurmaDTO dto = new TurmaDTO();
        dto.setId(turma.getId());
        dto.setNumero(turma.getNumero());
        dto.setSemestre(turma.getSemestre());
        dto.setHorario(turma.getHorario());
        dto.setVagas(turma.getVagas());

        // Mapeia a disciplina completa (para o campo 'disciplina' objeto em TurmaDTO)
        dto.setDisciplina(turma.getDisciplina() != null ? DisciplinaMapper.toDTO(turma.getDisciplina()) : null);
        // O disciplinaId para entrada ainda estará em TurmaDTO, mas não é setado aqui para saída.
        // dto.setDisciplinaId(turma.getDisciplina() != null ? turma.getDisciplina().getId() : null);

        // Mapear o objeto Professor completo usando UserMapper (para o campo 'professor' objeto em TurmaDTO)
        dto.setProfessor(turma.getProfessor() != null ? UserMapper.toUserResponseDTO(turma.getProfessor()) : null);
        // O professorId para entrada ainda estará em TurmaDTO, mas não é setado aqui para saída.
        // dto.setProfessorId(turma.getProfessor() != null ? turma.getProfessor().getId() : null);


        dto.setAlunos(
        turma.getAlunos() != null
            ? turma.getAlunos().stream()
                .map(aluno -> new AlunoResponseDTO(aluno.getId(), aluno.getUsername(), aluno.getEmail()))
                .collect(Collectors.toList())
            : null
        );

        return dto;
    }

    public static Turma toEntity(TurmaDTO dto) { // Usado para requisições de entrada (criar/atualizar Turma)
        if (dto == null) return null;

        Turma turma = new Turma();
        turma.setId(dto.getId());
        turma.setNumero(dto.getNumero());
        turma.setSemestre(dto.getSemestre());
        turma.setHorario(dto.getHorario());
        turma.setVagas(dto.getVagas());

        // Mapeamento de Disciplina para Entidade: Priorize o ID, pois é o que a entidade precisa
        if (dto.getDisciplinaId() != null) { // Use o ID diretamente se fornecido
             Disciplina disciplina = new Disciplina();
             disciplina.setId(dto.getDisciplinaId());
             turma.setDisciplina(disciplina);
        } else if (dto.getDisciplina() != null && dto.getDisciplina().getId() != null) { // Ou se o DTO de entrada veio com o objeto completo
            Disciplina disciplina = new Disciplina();
            disciplina.setId(dto.getDisciplina().getId());
            turma.setDisciplina(disciplina);
        } else {
            // Se disciplinaId é obrigatório, o @NotNull no DTO já cuidará disso.
            // Se não for obrigatório e não veio, o campo em turma ficará null.
        }

        // Mapeamento de Professor para Entidade: Priorize o ID, pois é o que a entidade precisa
        if (dto.getProfessorId() != null) { // Use o ID diretamente se fornecido
            User professor = new User();
            professor.setId(dto.getProfessorId());
            turma.setProfessor(professor);
        } else if (dto.getProfessor() != null && dto.getProfessor().getId() != null) { // Ou se o DTO de entrada veio com o objeto completo
            User professor = new User();
            professor.setId(dto.getProfessor().getId());
            turma.setProfessor(professor);
        } else {
            
        }


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
            turma.getProfessor() != null ? UserMapper.toUserResponseDTO(turma.getProfessor()) : null, // Mapeia o professor como UserResponseDTO
            turma.getHorario(),
            turma.getVagas(),
            turma.getAlunos() != null
                ? turma.getAlunos().stream()
                    .map(aluno -> new AlunoResponseDTO(aluno.getId(),aluno.getUsername(),aluno.getEmail()))
                    .collect(Collectors.toList())
                : null
        );
    }
}