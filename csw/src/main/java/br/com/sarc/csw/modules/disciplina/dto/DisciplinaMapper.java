package br.com.sarc.csw.modules.disciplina.dto;

import br.com.sarc.csw.modules.disciplina.model.Disciplina;

public class DisciplinaMapper {

    public static DisciplinaDTO toDTO(Disciplina disciplina) {
        if (disciplina == null) {
            return null;
        }

        return new DisciplinaDTO(
            disciplina.getId(),
            disciplina.getCodigo(),
            disciplina.getNome(),
            disciplina.getCreditos(),
            disciplina.getSemestre(),
            disciplina.getPrograma()
        );
    }

    public static Disciplina toEntity(DisciplinaDTO dto) {
        if (dto == null) {
            return null;
        }

        Disciplina disciplina = new Disciplina();
        disciplina.setId(dto.getId());
        disciplina.setCodigo(dto.getCodigo());
        disciplina.setNome(dto.getNome());
        disciplina.setCreditos(dto.getCreditos());
        disciplina.setSemestre(dto.getSemestre());
        disciplina.setPrograma(dto.getPrograma());
        return disciplina;
    }
}
