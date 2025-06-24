package br.com.sarc.csw.modules.aula.model;

import br.com.sarc.csw.core.enums.PeriodoAula;
import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.user.model.User;
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

import java.time.LocalDate; // Import LocalDate

@Entity
@Table(name = "aula")
@Data
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    private LocalDate data; // Changed from String to LocalDate

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @Enumerated(EnumType.STRING)
    private PeriodoAula periodo;

    private String descricao;
}