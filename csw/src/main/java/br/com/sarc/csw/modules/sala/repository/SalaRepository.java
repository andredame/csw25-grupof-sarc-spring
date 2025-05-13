package br.com.sarc.csw.modules.sala.repository;

import br.com.sarc.csw.modules.sala.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findByPredioId(Long predioId);
    List<Sala> findByPredioIdAndCapacidadeGreaterThanEqual(Long predioId, int capacidadeMinima);
}
