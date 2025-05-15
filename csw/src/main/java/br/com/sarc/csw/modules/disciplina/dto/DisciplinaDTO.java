package br.com.sarc.csw.modules.disciplina.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisciplinaDTO {

    private Long id;

    @NotBlank(message = "O código da disciplina é obrigatório")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "O código da disciplina deve conter apenas caracteres alfanuméricos")
    private String codigo;

    @NotBlank(message = "O nome da disciplina é obrigatório")
    private String nome;

    @NotNull(message = "O número de créditos é obrigatório")
    private Integer creditos;

    @NotBlank(message = "O semestre é obrigatório")
    private String semestre;

    @NotBlank(message = "O programa é obrigatório")
    private String programa;
}
