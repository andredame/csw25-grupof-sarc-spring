package br.com.sarc.csw.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UserResponse {
    private String id;
    private String username;
    private boolean enabled;
}
