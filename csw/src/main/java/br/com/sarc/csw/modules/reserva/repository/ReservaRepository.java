package br.com.sarc.csw.modules.reserva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sarc.csw.modules.reserva.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Método para buscar reservas por recursoId
    List<Reserva> findAllByRecursoId(Long recursoId);

    // Outros métodos de consulta, se necessário    

}