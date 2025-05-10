package br.com.sarc.csw.modules.aula.dto;

import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.turma.model.Turma;

public class AulaMapper {

    public static AulaDTO toDTO(Aula aula) {
        if (aula == null) {
            return null;
        }
        return new AulaDTO(
            aula.getId(),
            aula.getData(),
            aula.getTurma() != null ? aula.getTurma().getId() : null,
            aula.getSala() != null ? aula.getSala().getId() : null
        );
    }

    public static Aula toEntity(AulaDTO aulaDTO, Recurso recurso, Turma turma, Sala sala) {
        if (aulaDTO == null) {
            return null;
        }
        Aula aula = new Aula();
        aula.setId(aulaDTO.getId());
        aula.setData(aulaDTO.getData());
        aula.setTurma(turma);     // Turma deve ser buscada pelo ID
        aula.setSala(sala);       // Sala deve ser buscada pelo ID
        return aula;
    }
}