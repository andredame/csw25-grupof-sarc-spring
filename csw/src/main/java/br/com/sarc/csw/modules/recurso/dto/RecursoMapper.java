package br.com.sarc.csw.modules.recurso.dto;

import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.model.TipoRecurso;

public class RecursoMapper {

    public static RecursoDTO toDTO(Recurso recurso) {
        return new RecursoDTO(
                recurso.getId(),
                recurso.getStatus(),
                new TipoRecursoDTO(recurso.getTipoRecurso().getId(), recurso.getTipoRecurso().getNome())
        );
    }

    public static Recurso toEntity(RecursoDTO recursoDTO) {
        Recurso recurso = new Recurso();
        recurso.setId(recursoDTO.getId());
        recurso.setStatus(recursoDTO.getStatus());
        recurso.setTipoRecurso(new TipoRecurso());
        recurso.getTipoRecurso().setId(recursoDTO.getTipoRecurso().getId());
        recurso.getTipoRecurso().setNome(recursoDTO.getTipoRecurso().getNome());
        return recurso;
    }
}