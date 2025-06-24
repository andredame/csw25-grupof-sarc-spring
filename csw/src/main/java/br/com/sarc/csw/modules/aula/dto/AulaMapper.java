package br.com.sarc.csw.modules.aula.dto;

import br.com.sarc.csw.modules.aula.model.Aula;
// Removed unused import: import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.sala.dto.SalaMapper;
import br.com.sarc.csw.modules.sala.dto.SalaResponseDTO;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.turma.dto.TurmaMapper;
import br.com.sarc.csw.modules.turma.dto.TurmaDTO;
import br.com.sarc.csw.modules.turma.model.Turma;

public class AulaMapper {

    // Converte AulaRequestDTO para a entidade Aula
    public static Aula toEntity(AulaRequestDTO dto) {
        if (dto == null) return null;

        Aula aula = new Aula();
        aula.setData(dto.getData()); // Set LocalDate directly
        aula.setDescricao(dto.getDescricao());
        aula.setPeriodo(dto.getPeriodo());

        if (dto.getTurmaId() != null) {
            Turma turma = new Turma();
            turma.setId(dto.getTurmaId());
            // Removed disciplinaId logic as it's no longer in AulaRequestDTO
            // if (dto.getDisciplinaId() != null) {
            //     Disciplina disciplina = new Disciplina();
            //     disciplina.setId(dto.getDisciplinaId());
            //     turma.setDisciplina(disciplina);
            // }
            aula.setTurma(turma);
        }

        if (dto.getSalaId() != null) {
            Sala sala = new Sala();
            sala.setId(dto.getSalaId());
            aula.setSala(sala);
        }

        return aula;
    }

    // Converte a entidade Aula para AulaResponseDTO
    public static AulaResponseDTO toResponseDTO(Aula aula) {
        if (aula == null) return null;

        TurmaDTO turmaDTO = TurmaMapper.toDTO(aula.getTurma());
        SalaResponseDTO salaDTO = SalaMapper.toResponseDTO(aula.getSala());

        return new AulaResponseDTO(
            aula.getId(),
            aula.getData(), // Get LocalDate directly
            aula.getDescricao(),
            turmaDTO,
            salaDTO,
            aula.getPeriodo()
        );
    }
}