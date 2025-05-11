package br.com.sarc.csw.modules.recurso.service;

import br.com.sarc.csw.core.enums.StatusRecurso;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    public boolean verificarDisponibilidade(Long recursoId) {
        System.out.println("Verificando disponibilidade do recurso com ID: " + recursoId);
        Recurso recurso = recursoRepository.findById(recursoId).orElse(null);
        return recurso.getStatus() != null && recurso.getStatus() == StatusRecurso.DISPONIVEL;

    }

    public void atualizarStatus(Long recursoId, String novoStatus) {
        Recurso recurso = recursoRepository.findById(recursoId).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
        recurso.setStatus(StatusRecurso.valueOf(novoStatus));
        recursoRepository.save(recurso);
    }
}