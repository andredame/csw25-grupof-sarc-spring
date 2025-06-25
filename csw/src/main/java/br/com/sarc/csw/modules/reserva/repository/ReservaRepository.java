package br.com.sarc.csw.modules.reserva.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.sarc.csw.modules.reserva.model.Reserva;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
    @Query("SELECT DISTINCT r FROM Reserva r " +
           "JOIN FETCH r.aula a " +
           "LEFT JOIN FETCH a.turma t " + // LEFT JOIN FETCH para turma
           "LEFT JOIN FETCH t.disciplina d " + // LEFT JOIN FETCH para disciplina
           "LEFT JOIN FETCH t.professor p " + // LEFT JOIN FETCH para professor
           "JOIN FETCH a.sala s " +
           "LEFT JOIN FETCH s.predio pr " + // LEFT JOIN FETCH para prédio
           "JOIN FETCH r.recurso rec " +
           "LEFT JOIN FETCH rec.tipoRecurso tr") // LEFT JOIN FETCH para tipoRecurso
    List<Reserva> findAllWithDetails();

    List<Reserva> findByAulaId(Long aulaId); // Este método já existe e é usado no AulaService.deletar

    @Query("SELECT DISTINCT r FROM Reserva r " +
           "JOIN FETCH r.aula a " +
           "LEFT JOIN FETCH a.turma t " +
           "LEFT JOIN FETCH t.disciplina d " +
           "LEFT JOIN FETCH t.professor p " +
           "JOIN FETCH a.sala s " +
           "LEFT JOIN FETCH s.predio pr " +
           "JOIN FETCH r.recurso rec " +
           "LEFT JOIN FETCH rec.tipoRecurso tr " +
           "WHERE rec.id = :recursoId")
    List<Reserva> findAllWithDetailsByRecursoId(@Param("recursoId") Long recursoId);

    @Query("SELECT DISTINCT r FROM Reserva r " +
           "JOIN FETCH r.aula a " +
           "JOIN FETCH a.turma t " +
           "LEFT JOIN FETCH t.disciplina d " +
           "LEFT JOIN FETCH t.professor p " + // JOIN FETCH o professor da turma
           "JOIN FETCH a.sala s " +
           "LEFT JOIN FETCH s.predio pr " +
           "JOIN FETCH r.recurso rec " +
           "LEFT JOIN FETCH rec.tipoRecurso tr " +
           "WHERE p.id = :professorId") // Filtra pelo ID do professor
    List<Reserva> findAllByAulaProfessorIdWithDetails(@Param("professorId") UUID professorId);
}