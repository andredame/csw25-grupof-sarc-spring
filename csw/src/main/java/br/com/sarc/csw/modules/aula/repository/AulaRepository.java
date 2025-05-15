package br.com.sarc.csw.modules.aula.repository;
import br.com.sarc.csw.modules.aula.model.Aula;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Exemplo: List<Aula> findByProfessorId(Long professorId);
    List<Aula> findByTurmaIdAndTurmaProfessorId(Long turmaId, UUID professorId);

    List<Aula> findByTurmaProfessorId(UUID professorId);
    List<Aula> findByTurmaId(Long turmaId);
    List<Aula> findByData(String data);
}
    

