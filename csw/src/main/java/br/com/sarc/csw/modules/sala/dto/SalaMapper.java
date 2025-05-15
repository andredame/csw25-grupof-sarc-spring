package br.com.sarc.csw.modules.sala.dto;

import br.com.sarc.csw.modules.predio.dto.PredioResumoDTO;
import br.com.sarc.csw.modules.predio.model.Predio;
import br.com.sarc.csw.modules.sala.model.Sala;


public class SalaMapper {

    public static Sala toEntity(SalaRequestDTO dto) {
        if (dto == null) return null;

        Sala sala = new Sala();
        sala.setNome(dto.getNome());
        sala.setCapacidade(dto.getCapacidade());
        sala.setAndar(dto.getAndar());
        return sala;
    }

    public static SalaResponseDTO toResponseDTO(Sala sala) {
        if (sala == null) return null;

        Predio predio = sala.getPredio();
        PredioResumoDTO predioDTO = predio != null
                ? new PredioResumoDTO(predio.getId(), predio.getNome())
                : null;

        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.getAndar(),
                predioDTO
        );
    }
}
