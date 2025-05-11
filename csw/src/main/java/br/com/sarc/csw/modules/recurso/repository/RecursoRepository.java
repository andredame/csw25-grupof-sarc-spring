package br.com.sarc.csw.modules.recurso.repository;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {

  
}
