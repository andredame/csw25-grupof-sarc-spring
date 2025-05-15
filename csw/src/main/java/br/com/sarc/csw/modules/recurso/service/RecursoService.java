package br.com.sarc.csw.modules.recurso.service;

import br.com.sarc.csw.core.enums.StatusRecurso;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.repository.RecursoRepository;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    public boolean verificarDisponibilidade(Long recursoId) {
        Recurso recurso = recursoRepository.findById(recursoId).orElse(null);
        return recurso.getStatus() != null && recurso.getStatus() == StatusRecurso.DISPONIVEL;

    }

    public void atualizarStatus(Long recursoId, String novoStatus) {
        Recurso recurso = recursoRepository.findById(recursoId).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
        recurso.setStatus(StatusRecurso.valueOf(novoStatus));
        recursoRepository.save(recurso);
    }

    public List<Recurso> listarTodos() {
        return recursoRepository.findAll();
    }

    public Recurso obterPorId(Long id) {
        return recursoRepository.findById(id).orElse(null);
    }

    public Recurso criar(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public Recurso atualizar(Long id, Recurso recurso) {
        Optional<Recurso> recursoExistente = recursoRepository.findById(id);
        if (recursoExistente.isPresent()) {
            recurso.setId(id); // Garante que o ID seja mantido
            return recursoRepository.save(recurso);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (recursoRepository.existsById(id)) {
            recursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    
}