package br.com.sarc.csw.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate; // ADICIONADO: Para dateOfBirth
import java.util.UUID; // ADICIONADO: Para id

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id; // CORRIGIDO: Tipo para UUID

    @NotBlank(message = "O nome é obrigatório")
    private String username;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private String phoneNumber; // ADICIONADO: Inclua se for mapeado em UserMapper
    private LocalDate dateOfBirth; // ADICIONADO: Inclua se for mapeado em UserMapper
}