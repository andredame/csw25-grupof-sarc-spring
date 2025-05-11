package br.com.sarc.csw.modules.turma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sarc.csw.modules.turma.model.Turma;
import java.util.List;
import java.util.UUID;

import br.com.sarc.csw.modules.user.model.User;


@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    List<Turma> findByProfessorId(UUID professorId);

    
    
    
}
