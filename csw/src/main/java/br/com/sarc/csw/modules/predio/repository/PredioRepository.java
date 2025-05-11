package br.com.sarc.csw.modules.predio.repository;

import org.springframework.stereotype.Repository;

import br.com.sarc.csw.modules.predio.model.Predio;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PredioRepository extends JpaRepository<Predio, Long> {
 
    
}
