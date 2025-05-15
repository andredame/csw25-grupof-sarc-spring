package br.com.sarc.csw.modules.user.dto;
import br.com.sarc.csw.modules.user.model.User;

public class UserMapper {
    public static AlunoResponseDTO toAlunoResponseDTO(User user) {
        AlunoResponseDTO dto = new AlunoResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
