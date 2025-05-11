package br.com.sarc.csw.modules.sala.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sarc.csw.modules.sala.model.Sala;
import java.util.List;


@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findByPredioId(Long id);

    
    
}
