package br.com.sarc.csw.modules.recurso.service;

import br.com.sarc.csw.core.enums.StatusRecurso;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    public boolean verificarDisponibilidade(Long recursoId) {
        Recurso recurso = recursoRepository.findById(recursoId).orElse(null);
        return recurso != null && recurso.getStatus() == StatusRecurso.DISPONIVEL;
    }

    public void atualizarStatus(Long recursoId, String novoStatus) {
        Recurso recurso = recursoRepository.findById(recursoId).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
        recurso.setStatus(StatusRecurso.valueOf(novoStatus));
        recursoRepository.save(recurso);
    }

    public Recurso criar(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public List<Recurso> listarTodos() {
        return recursoRepository.findAll();
    }

    public Recurso obterPorId(Long id) {
        return recursoRepository.findById(id).orElse(null);
    }

    public Recurso atualizar(Long id, Recurso recursoAtualizado) {
        Recurso recurso = recursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
        recurso.setStatus(recursoAtualizado.getStatus());
        recurso.setTipoRecurso(recursoAtualizado.getTipoRecurso());
        return recursoRepository.save(recurso);
    }

    public boolean deletar(Long id) {
        if (recursoRepository.existsById(id)) {
            recursoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean alterarStatus(Long id, String status) {
        Recurso recurso = recursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
        recurso.setStatus(StatusRecurso.valueOf(status));
        recursoRepository.save(recurso);
        return true;
    }
}