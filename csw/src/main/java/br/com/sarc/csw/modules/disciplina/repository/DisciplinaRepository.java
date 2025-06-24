package br.com.sarc.csw.modules.disciplina.repository;

import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    // Adicionar este método para verificar a unicidade do código
    Optional<Disciplina> findByCodigo(String codigo);
}