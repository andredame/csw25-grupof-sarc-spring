package br.com.sarc.csw.modules.sala.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SalaRequestDTO {

    @NotBlank(message = "O nome da sala é obrigatório")
    private String nome;

    @NotNull(message = "A capacidade da sala é obrigatória")
    @Min(value = 1, message = "A capacidade deve ser de no mínimo 1")
    private Integer capacidade;

    @NotBlank(message = "O andar da sala é obrigatório")
    private String andar;


}
