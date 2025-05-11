package br.com.sarc.csw.modules.reserva.dto;

import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.reserva.model.Reserva;

public class ReservaMapper {

    public static ReservaDTO toDTO(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        ReservaDTO dto = new ReservaDTO();

        if (reserva.getAula() != null) {
            dto.setId_aula(reserva.getAula().getId());
        }

        if (reserva.getRecurso() != null) {
            dto.setId_recurso(reserva.getRecurso().getId());
        }

        return dto;
    }

    public static Reserva toEntity(ReservaDTO dto) {
        if (dto == null) {
            return null;
        }

        Reserva reserva = new Reserva();

        if (dto.getId_aula() != null) {
            Aula aula = new Aula();
            aula.setId(dto.getId_aula());
            reserva.setAula(aula);
        }

        if (dto.getId_recurso() != null) {
            Recurso recurso = new Recurso();
            recurso.setId(dto.getId_recurso());
            reserva.setRecurso(recurso);
        }

        return reserva;
    }
}
