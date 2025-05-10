package br.com.sarc.csw.modules.predio.dto;

import br.com.sarc.csw.modules.predio.model.Predio;

public class PredioMapper {

    public static PredioDTO toDTO(Predio predio) {
        if (predio == null) return null;

        return new PredioDTO(
            predio.getId(),
            predio.getNome(),
            predio.getRua(),
            predio.getNumero(),
            predio.getComplemento(),
            predio.getBairro(),
            predio.getCidade(),
            predio.getUf(),
            predio.getCep()
        );
    }

    public static Predio toEntity(PredioDTO dto) {
        if (dto == null) return null;

        Predio predio = new Predio();
        predio.setId(dto.getId());
        predio.setNome(dto.getNome());
        predio.setRua(dto.getRua());
        predio.setNumero(dto.getNumero());
        predio.setComplemento(dto.getComplemento());
        predio.setBairro(dto.getBairro());
        predio.setCidade(dto.getCidade());
        predio.setUf(dto.getUf());
        predio.setCep(dto.getCep());

        return predio;
    }
}
