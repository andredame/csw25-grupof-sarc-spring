package br.com.sarc.csw.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto { // CORRIGIDO: Nome da classe para UserResponseDTO
    private UUID id;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private List<String> roles; // ESTE CAMPO DEVE ESTAR PRESENTE
}