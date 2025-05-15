package br.com.sarc.csw.modules.user.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoResponseDTO {
    private UUID id;
    private String username;
    private String email;
}
