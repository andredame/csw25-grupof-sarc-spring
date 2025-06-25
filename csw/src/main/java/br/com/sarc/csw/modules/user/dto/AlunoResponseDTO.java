package br.com.sarc.csw.modules.user.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// import java.util.List; // REMOVIDO: Não necessário se roles for removido

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoResponseDTO {
    private UUID id;
    private String username;
    private String email;

}