package br.com.sarc.csw.modules.recurso.dto;

import br.com.sarc.csw.modules.recurso.model.Recurso;

public class RecursoMapper {

    public static RecursoDTO toDTO(Recurso recurso) {
        return new RecursoDTO(
                recurso.getId(),
                recurso.getStatus(),
                new TipoRecursoDTO(
                        recurso.getTipoRecurso().getId(),
                        recurso.getTipoRecurso().getNome()
                )
        );
    }

    public static Recurso toEntity(RecursoDTO recursoDTO) {
        Recurso recurso = new Recurso();
        recurso.setId(recursoDTO.getId());
        recurso.setStatus(recursoDTO.getStatus());

        var tipoRecurso = new br.com.sarc.csw.modules.recurso.model.TipoRecurso();
        tipoRecurso.setId(recursoDTO.getTipoRecurso().getId());
        tipoRecurso.setNome(recursoDTO.getTipoRecurso().getNome());

        recurso.setTipoRecurso(tipoRecurso);
        return recurso;
    }
}