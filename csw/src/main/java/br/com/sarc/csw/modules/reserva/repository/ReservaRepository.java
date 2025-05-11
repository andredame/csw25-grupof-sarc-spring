package br.com.sarc.csw.modules.reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sarc.csw.modules.reserva.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}