package br.com.sarc.csw.modules.sala.model;

import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.predio.model.Predio;
import br.com.sarc.csw.modules.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "sala")
@Data
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer capacidade;

    private String andar;

    @ManyToOne
    @JoinColumn(name = "predio_id", nullable = false)
    private Predio predio;
}
