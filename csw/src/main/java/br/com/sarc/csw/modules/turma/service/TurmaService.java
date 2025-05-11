package br.com.sarc.csw.modules.turma.service;

import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    public List<Turma> listarTurmasPorProfessor(UUID professorId) {
        return turmaRepository.findByProfessorId(professorId);
    }

    public Turma obterPorId(Long id) {
        return turmaRepository.findById(id).orElse(null);
    }

    public Turma atualizarTurma(Long id, Turma turmaAtualizada) {
        if (turmaRepository.existsById(id)) {
            turmaAtualizada.setId(id);
            return turmaRepository.save(turmaAtualizada);
        }
        return null;
    }
}