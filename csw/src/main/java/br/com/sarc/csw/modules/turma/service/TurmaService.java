package br.com.sarc.csw.modules.turma.service;

import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository turmaRepository;

    public List<Turma> listarTurmasPorProfessor(UUID professorId) {
        return turmaRepository.findByProfessorId(professorId);
    }

    public List<Turma> listarTodasTurmas() {
        return turmaRepository.findAll();
    }

    public Turma obterPorId(Long id) {
        return turmaRepository.findById(id).orElse(null);
    }

    public Turma criarTurma(Turma turma) {
        return turmaRepository.save(turma);
    }

    public Turma atualizarTurma(Long id, Turma turma) {
        if (turmaRepository.existsById(id)) {
            turma.setId(id);
            return turmaRepository.save(turma);
        }
        return null;
    }

    public void deletarTurma(Long id) {
        turmaRepository.deleteById(id);
    }
}