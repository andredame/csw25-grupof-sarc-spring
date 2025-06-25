package br.com.sarc.csw.modules.user.dto;

import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.model.Role; // CORRIGIDO: Importe Role (singular)
import java.util.stream.Collectors; // Importe Collectors

public class UserMapper {

    public static UserResponseDto toUserResponseDTO(User user) {
        if (user == null) return null;

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber()); // Mapear outros campos se UserResponseDTO os tiver
        // Converte String para LocalDate, se necessário
        if (user.getDateOfBirth() != null && !user.getDateOfBirth().isEmpty()) {
            dto.setDateOfBirth(java.time.LocalDate.parse(user.getDateOfBirth()));
        }

        // CRUCIAL: Mapear as roles (agora que é uma lista em User.java)
        if (user.getRoles() != null) { // user.getRoles() retorna List<Role>
            dto.setRoles(user.getRoles().stream()
                                  .map(Role::getName) // Mapeia cada objeto Role para seu nome (string)
                                  .collect(Collectors.toList()));
        } else {
            dto.setRoles(java.util.Collections.emptyList()); // Garante que a lista nunca seja null
        }

        return dto;
    }

    public static User toEntity(UserDto dto) { // Mantenha este método se usado para entrada
        
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setDateOfBirth(dto.getDateOfBirth() != null ? dto.getDateOfBirth().toString() : null);
        return user;
    }

    public static AlunoResponseDTO toAlunoResponseDTO(User user) { // Mantenha se usado
        if (user == null) return null;
        return new AlunoResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}