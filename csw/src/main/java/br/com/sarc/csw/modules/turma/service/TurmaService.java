package br.com.sarc.csw.modules.turma.service;

import br.com.sarc.csw.modules.disciplina.repository.DisciplinaRepository;
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
    @Autowired // Removed 'final' for @Autowired field, as @RequiredArgsConstructor injects final fields
    private TurmaRepository turmaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository; // Injetar DisciplinaRepository


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
        // Validação: Garantir que a disciplina associada existe
        if (turma.getDisciplina() == null || !disciplinaRepository.existsById(turma.getDisciplina().getId())) {
            throw new IllegalArgumentException("Disciplina associada à turma não encontrada.");
        }
        // Validação: Garantir que o professor associado existe
        if (turma.getProfessor() == null || !userRepository.existsById(turma.getProfessor().getId())) {
            throw new IllegalArgumentException("Professor associado à turma não encontrado.");
        }
        return turmaRepository.save(turma);
    }

    public Turma atualizarTurma(Long id, Turma turmaAtualizada) {
        return turmaRepository.findById(id).map(turmaExistente -> {
            // Validação: Garantir que a disciplina associada existe, se for atualizada
            if (turmaAtualizada.getDisciplina() == null || !disciplinaRepository.existsById(turmaAtualizada.getDisciplina().getId())) {
                throw new IllegalArgumentException("Disciplina associada à turma não encontrada.");
            }
            // Validação: Garantir que o professor associado existe, se for atualizado
            if (turmaAtualizada.getProfessor() == null || !userRepository.existsById(turmaAtualizada.getProfessor().getId())) {
                throw new IllegalArgumentException("Professor associado à turma não encontrado.");
            }

            turmaExistente.setNumero(turmaAtualizada.getNumero());
            turmaExistente.setDisciplina(turmaAtualizada.getDisciplina());
            turmaExistente.setSemestre(turmaAtualizada.getSemestre());
            turmaExistente.setProfessor(turmaAtualizada.getProfessor());
            turmaExistente.setHorario(turmaAtualizada.getHorario());
            turmaExistente.setVagas(turmaAtualizada.getVagas());
            // A lista de alunos deve ser atualizada por um método específico (vincularAlunoATurma/desvincular)
            // turmaExistente.setAlunos(turmaAtualizada.getAlunos()); // CUIDADO: Evitar substituir a lista de alunos diretamente aqui para manter a lógica de vagas
            return turmaRepository.save(turmaExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Turma não encontrada com o ID: " + id));
    }

    public void deletarTurma(Long id) {
        turmaRepository.deleteById(id);
    }

    public List<User> listarAlunosPorTurma(Long id) {
        Turma turma = turmaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Turma não encontrada")); // Changed RuntimeException to IllegalArgumentException
        return turma.getAlunos();
    }


    public User vincularAlunoATurma(Long turmaId, UUID alunoId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));
        User aluno = userRepository.findById(alunoId).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        // Validação: Checar se o aluno já está na turma
        if (turma.getAlunos().contains(aluno)) {
            throw new IllegalArgumentException("Aluno já está matriculado nesta turma.");
        }

        // Validação: Checar se há vagas disponíveis
        if (turma.getVagas() != null && turma.getAlunos().size() >= turma.getVagas()) {
            throw new IllegalStateException("Não há vagas disponíveis nesta turma.");
        }

        turma.getAlunos().add(aluno);
        turmaRepository.save(turma);
        return aluno;
    }

    public Turma vincularProfessorATurma(Long turmaId, UUID professorId) {
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));
        User professor = userRepository.findById(professorId).orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));

        // Validação: Só altera se for diferente
        if (turma.getProfessor() == null || !turma.getProfessor().getId().equals(professorId)) {
            turma.setProfessor(professor);
            turmaRepository.save(turma);
        } else {
            throw new IllegalArgumentException("O professor já está associado a esta turma.");
        }

        return turma;
    }
}