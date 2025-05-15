package br.com.sarc.csw.modules.predio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PredioDTO {
    private Long id; // caso esteja usando para update

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "A rua é obrigatória")
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    private String numero;

    private String complemento;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O estado (UF) é obrigatório")
    @Size(min = 2, max = 2, message = "UF deve ter 2 letras")
    private String uf;

    @NotBlank(message = "O CEP é obrigatório")
    @Size(min = 8, max = 9, message = "CEP deve ter entre 8 e 9 caracteres")
    private String cep;
}