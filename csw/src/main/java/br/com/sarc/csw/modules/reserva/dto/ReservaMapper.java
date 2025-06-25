package br.com.sarc.csw.modules.reserva.dto;

import br.com.sarc.csw.modules.aula.dto.AulaMapper;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.recurso.dto.RecursoMapper;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.reserva.model.Reserva;

public class ReservaMapper {

    public static ReservaDTO toDTO(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        ReservaDTO dto = new ReservaDTO();

        if (reserva.getAula() != null) {
            dto.setAulaId(reserva.getAula().getId());
        }

        if (reserva.getRecurso() != null) {
            dto.setRecursoId(reserva.getRecurso().getId());
        }

        return dto;
    }

    public static Reserva toEntity(ReservaDTO dto) {
        if (dto == null) {
            return null;
        }

        Reserva reserva = new Reserva();

        if (dto.getAulaId() != null) {
            Aula aula = new Aula();
            aula.setId(dto.getAulaId());
            reserva.setAula(aula);
        }

        if (dto.getRecursoId() != null) {
            Recurso recurso = new Recurso();
            recurso.setId(dto.getRecursoId());
            reserva.setRecurso(recurso);
        }

        return reserva;
    }

    public static ReservaResponseDTO toResponseDTO(Reserva reserva) {
        if (reserva == null) return null;
        return new ReservaResponseDTO(
            reserva.getId(),
            AulaMapper.toResponseDTO(reserva.getAula()),
            RecursoMapper.toDTO(reserva.getRecurso())
        );
    }
}
