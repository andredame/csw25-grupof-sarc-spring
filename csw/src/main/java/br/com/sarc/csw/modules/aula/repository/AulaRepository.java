package br.com.sarc.csw.modules.aula.repository;

import br.com.sarc.csw.modules.aula.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    // Lista todas as aulas com todos os detalhes relacionados para evitar N+1
    @Query("SELECT DISTINCT a FROM Aula a " +
           "JOIN FETCH a.turma t " +
           "LEFT JOIN FETCH t.disciplina d " + // Usar LEFT JOIN FETCH para disciplina e professor caso sejam opcionais
           "LEFT JOIN FETCH t.professor p " +
           "JOIN FETCH a.sala s " +
           "LEFT JOIN FETCH s.predio pr") // LEFT JOIN FETCH para prédio caso seja opcional
    List<Aula> findAllWithDetails();

    // Busca aulas por professor, carregando todos os detalhes
    @Query("SELECT DISTINCT a FROM Aula a " +
           "JOIN FETCH a.turma t " +
           "LEFT JOIN FETCH t.disciplina d " +
           "LEFT JOIN FETCH t.professor p " +
           "JOIN FETCH a.sala s " +
           "LEFT JOIN FETCH s.predio pr " +
           "WHERE p.id = :professorId") // Filtra pelo ID do professor da turma
    List<Aula> findByTurmaProfessorIdWithDetails(@Param("professorId") UUID professorId);

    // Busca aulas por data, carregando todos os detalhes
    @Query("SELECT DISTINCT a FROM Aula a " +
           "JOIN FETCH a.turma t " +
           "LEFT JOIN FETCH t.disciplina d " +
           "LEFT JOIN FETCH t.professor p " +
           "JOIN FETCH a.sala s " +
           "LEFT JOIN FETCH s.predio pr " +
           "WHERE a.data = :data")
    List<Aula> findByDataWithDetails(@Param("data") LocalDate data);


    // Métodos originais (se ainda utilizados e com carregamento básico)
    // Opcional: Manter findByTurmaIdAndTurmaProfessorId se precisar de uma query mais leve
    List<Aula> findByTurmaIdAndTurmaProfessorId(Long turmaId, UUID professorId);
    List<Aula> findByTurmaId(Long turmaId);
    boolean existsBySalaIdAndDataAndPeriodo(Long salaId, LocalDate data, br.com.sarc.csw.core.enums.PeriodoAula periodo);
    // Nota: findByTurmaProfessorId agora tem uma versão WithDetails, considere migrar o uso no service
}