package br.com.sarc.csw.auth.dto;

import lombok.Data;

@Data
public class RoleResponse {
    private String id;
    private String name;
    private String description;
    private boolean enabled;
}
