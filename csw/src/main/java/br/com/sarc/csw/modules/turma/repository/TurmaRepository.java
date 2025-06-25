package br.com.sarc.csw.modules.turma.repository;

import br.com.sarc.csw.modules.turma.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    // Lista todas as turmas com disciplina e professor carregados
    @Query("SELECT DISTINCT t FROM Turma t " +
           "JOIN FETCH t.disciplina d " +
           "JOIN FETCH t.professor p")
    List<Turma> findAllWithDetails();
    @Query("SELECT t FROM Turma t LEFT JOIN FETCH t.alunos WHERE t.id = :id")
    Optional<Turma> findByIdWithAlunos(@Param("id") Long id);

    // Lista turmas por professor, com disciplina e professor carregados
    @Query("SELECT DISTINCT t FROM Turma t " +
           "JOIN FETCH t.disciplina d " +
           "JOIN FETCH t.professor p " +
           "WHERE p.id = :professorId")
    List<Turma> findByProfessorIdWithDetails(@Param("professorId") UUID professorId);

    // Opcional: Se precisar de alunos junto, pode adicionar JOIN FETCH t.alunos al, mas CUIDADO com o produto cartesiano.
    // Para muitos alunos, é melhor carregar os alunos em uma query separada (ex: TurmaService.listarAlunosDeTurma)

    // Outros métodos existentes
    Optional<Turma> findById(Long id); // Mantém o método padrão se necessário para buscas simples
}