package br.com.sarc.csw.modules.aula.repository;

import br.com.sarc.csw.modules.aula.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    // Consulta para carregar todas as aulas com detalhes de Turma, Disciplina, Professor, Sala e Prédio
    // O FETCH na ManyToMany (alunos) pode causar um produto cartesiano.
    // Para muitos alunos por turma, é mais eficiente carregar alunos separadamente se a lista de aulas for grande,
    // ou usar "DISTINCT" se a lista de aulas não for grande, para evitar duplicações.
    // Para começar, vamos focar nos relacionamentos ManyToOne.
    @Query("SELECT DISTINCT a FROM Aula a " +
           "JOIN FETCH a.turma t " +
           "JOIN FETCH t.disciplina d " +
           "JOIN FETCH t.professor p " + // Adicionado JOIN FETCH para o professor da turma
           "JOIN FETCH a.sala s " +
           "JOIN FETCH s.predio pr")
    List<Aula> findAllWithDetails();

    // Se você também precisa dos alunos para cada turma na mesma consulta,
    // o que pode gerar mais resultados duplicados se uma turma tiver muitos alunos e aulas:
    // @Query("SELECT DISTINCT a FROM Aula a " +
    //        "JOIN FETCH a.turma t " +
    //        "JOIN FETCH t.disciplina d " +
    //        "JOIN FETCH t.professor p " +
    //        "JOIN FETCH t.alunos al " + // CUIDADO: pode gerar muitas linhas
    //        "JOIN FETCH a.sala s " +
    //        "JOIN FETCH s.predio pr")
    // List<Aula> findAllWithAllDetails();

    // Se você tiver outros métodos de busca (como por data), também precisará de versões JOIN FETCH
    @Query("SELECT DISTINCT a FROM Aula a " +
           "JOIN FETCH a.turma t " +
           "JOIN FETCH t.disciplina d " +
           "JOIN FETCH t.professor p " +
           "JOIN FETCH a.sala s " +
           "JOIN FETCH s.predio pr " +
           "WHERE a.data = :data")
    List<Aula> findByDataWithDetails(java.time.LocalDate data);

    // ... Mantenha seus outros métodos de repositório (findByTurmaId, etc.) se forem necessários
    List<Aula> findByTurmaIdAndTurmaProfessorId(Long turmaId, java.util.UUID professorId);
    List<Aula> findByTurmaId(Long turmaId);
    boolean existsBySalaIdAndDataAndPeriodo(Long salaId, java.time.LocalDate data, br.com.sarc.csw.core.enums.PeriodoAula periodo);
    List<Aula> findByTurmaProfessorId(java.util.UUID professorId);
}