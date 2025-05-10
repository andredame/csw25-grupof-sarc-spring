package br.com.sarc.csw.modules.curriculo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "curriculo")
@Data
public class Curriculo {
    private Long id;
    private Long idCurriculo;
    private String nome;
    //data de inicio de vigencia 
    private String dataInicioVigencia;
    //data de fim de vigencia
    private String dataFimVigencia;

    
}
