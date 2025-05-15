package br.com.sarc.csw.modules.turma.model;

import java.util.List;

import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.Data;


@Entity
@Table(name = "turma")
@Data
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    private String semestre;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private User professor;



    private String horario;

    @Valid
    private Integer vagas;


    @ManyToMany
    @JoinTable(
        name = "turma_aluno",
        joinColumns = @JoinColumn(name = "turma_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<User> alunos;

}
