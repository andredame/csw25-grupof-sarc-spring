package br.com.sarc.csw.modules.disciplina.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sarc.csw.modules.disciplina.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {


    Optional<Disciplina> findById(Long id);
    

    
}
