package br.com.sarc.csw.modules.aula.service;

import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.aula.repository.AulaRepository;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    public List<Aula> listarAulasPorTurmaEProfessor(Long turmaId, UUID professorId) {
        return aulaRepository.findByTurmaIdAndTurmaProfessorId(turmaId, professorId);
    }
    public List<Aula> listarAulasPorTurma(Long turmaId) {
        return aulaRepository.findByTurmaId(turmaId);
    }

    public Aula criarAula(Long turmaId, UUID professorId, Aula aula) {
        // Verifica se a turma pertence ao professor
        if (!turmaRepository.existsByIdAndProfessorId(turmaId, professorId)) {
            throw new RuntimeException("Turma não pertence ao professor.");
        }
        return aulaRepository.save(aula);
    }

    public Aula atualizarAula(Long aulaId, Aula aulaAtualizada) {
        if (aulaRepository.existsById(aulaId)) {
            aulaAtualizada.setId(aulaId);
            return aulaRepository.save(aulaAtualizada);
        }
        return null;
    }

    public Aula obterPorId(Long aulaId) {
        return aulaRepository.findById(aulaId).orElse(null);
    }

    public boolean deletarAula(Long aulaId) {
        if (aulaRepository.existsById(aulaId)) {
            aulaRepository.deleteById(aulaId);
            return true;
        }
        return false;
    }
    public List<Aula> listarTodasAulas() {
        return aulaRepository.findAll();
    }
    public Aula salvar(Aula aula) {
        return aulaRepository.save(aula);
    }
    public List<Aula> buscarTodas() {
        return aulaRepository.findAll();
    }
    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id).orElse(null);
    }
    public Aula atualizar(Long id, Aula aulaAtualizada) {
        if (aulaRepository.existsById(id)) {
            aulaAtualizada.setId(id);
            return aulaRepository.save(aulaAtualizada);
        }
        return null;
    }
    public void deletar(Long id) {
        aulaRepository.deleteById(id);
    }

    public List<Aula> listarAulasPorProfessor(UUID professorId) {
        return aulaRepository.findByTurmaProfessorId(professorId);
    }


}