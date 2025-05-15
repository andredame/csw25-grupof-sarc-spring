package br.com.sarc.csw.modules.turma.service;

import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;
import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TurmaService {
    
    @Autowired
    private final UserRepository userRepository;
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

    public List<User> listarAlunosPorTurma(Long id) {
        Turma turma = turmaRepository.findById(id).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        return turma.getAlunos(); // Supondo que a entidade Turma tenha uma lista de alunos
    }


    public User vincularAlunoATurma(Long turmaId, UUID alunoId) {
        Turma turma = turmaRepository.findById(turmaId).orElse(null);
        User aluno = userRepository.findById(alunoId).orElse(null);

        if (turma == null || aluno == null) {
            return null;
        }

        // Evita duplicidade
        if (!turma.getAlunos().contains(aluno)) {
            turma.getAlunos().add(aluno);
            turmaRepository.save(turma);
        }

        return aluno;
    }
   public Turma vincularProfessorATurma(Long turmaId, UUID professorId) {
        Turma turma = turmaRepository.findById(turmaId).orElse(null);
        User professor = userRepository.findById(professorId).orElse(null);

        if (turma == null || professor == null) {
            return null;
        }

        // Só altera se for diferente
        if (turma.getProfessor() == null || !turma.getProfessor().getId().equals(professorId)) {
            turma.setProfessor(professor);
            turmaRepository.save(turma);
        }

        return turma;
    }
}