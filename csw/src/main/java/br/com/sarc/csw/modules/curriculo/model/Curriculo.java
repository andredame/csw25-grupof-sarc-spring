package br.com.sarc.csw.modules.curriculo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "curriculo")
@Data
public class Curriculo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática do ID
    private Long id;
    private String nome;
    //data de inicio de vigencia 
    private String dataInicioVigencia;
    //data de fim de vigencia
    private String dataFimVigencia;

    
}
