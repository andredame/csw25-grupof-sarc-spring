package br.com.sarc.csw.modules.recurso.model;

import org.aspectj.lang.annotation.DeclareAnnotation;

import br.com.sarc.csw.core.enums.StatusRecurso;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recurso")
@Data
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Enumerated(EnumType.STRING)
    private StatusRecurso status;

    @ManyToOne
    @JoinColumn(name = "id_tipo_recurso", nullable = false)
    private Integer tipoRecursoId; 
}
