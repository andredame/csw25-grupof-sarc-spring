package br.com.sarc.csw.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String name;
    private String email;

}
