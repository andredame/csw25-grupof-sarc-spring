package br.com.sarc.csw.modules.sala.repository;

import br.com.sarc.csw.modules.sala.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Import Query

import java.util.List;
import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    // NOVO MÉTODO: Busca todas as salas e já faz JOIN FETCH com o prédio para evitar N+1
    @Query("SELECT s FROM Sala s JOIN FETCH s.predio")
    List<Sala> findAllWithPredio();

    List<Sala> findByPredioId(Long predioId);
    Optional<Sala> findByIdAndPredioId(Long id, Long predioId);
    List<Sala> findByPredioIdAndCapacidadeGreaterThanEqual(Long predioId, int capacidadeMinima);
}