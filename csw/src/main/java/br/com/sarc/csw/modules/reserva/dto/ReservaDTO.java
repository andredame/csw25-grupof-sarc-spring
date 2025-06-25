package br.com.sarc.csw.modules.reserva.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    @NotNull(message = "O ID do recurso é obrigatório")
    private Long recursoId;

    @NotNull(message = "O ID da aula é obrigatório")
    private Long aulaId;
}
