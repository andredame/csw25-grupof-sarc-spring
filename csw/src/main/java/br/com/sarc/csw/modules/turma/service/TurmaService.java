package br.com.sarc.csw.modules.turma.service;

import br.com.sarc.csw.modules.disciplina.repository.DisciplinaRepository;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;
import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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

     public List<Turma> listarTodasTurmas() {
        return turmaRepository.findAllWithDetails();
    }

    public List<Turma> listarTurmasPorProfessor(UUID professorId) {
        return turmaRepository.findByProfessorIdWithDetails(professorId);
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
        // 1. Verifique se a turma existe
        Turma turma = turmaRepository.findById(turmaId)
            .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada com ID: " + turmaId));
        
        // 2. Verifique se o aluno existe
        User aluno = userRepository.findById(alunoId)
            .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com ID: " + alunoId));
        
        // 3. Verifique se o usuário é realmente um aluno
        if (aluno.getRoles() == null || aluno.getRoles().stream()
            .noneMatch(role -> "ALUNO".equals(role.getName()))) {
            throw new IllegalArgumentException("O usuário não tem a role ALUNO");
        }
        
        // 4. Verifique se o aluno já está na turma
        if (turma.getAlunos().contains(aluno)) {
            throw new IllegalStateException("Aluno já está matriculado nesta turma");
        }
        
        // 5. Verifique vagas disponíveis
        if (turma.getVagas() != null && turma.getAlunos().size() >= turma.getVagas()) {
            throw new IllegalStateException("Não há vagas disponíveis nesta turma");
        }
        
        // 6. Adicione o aluno e salve
        turma.getAlunos().add(aluno);
        turmaRepository.save(turma);
        return aluno;
    }
    public Turma vincularProfessorATurma(Long turmaId, UUID professorId) {
        Turma turma = turmaRepository.findById(turmaId)
            .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada com ID: " + turmaId));
        
        User professor = userRepository.findById(professorId)
            .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado com ID: " + professorId));
        
        // Verifique se o usuário é realmente um professor
        if (professor.getRoles() == null || professor.getRoles().stream()
            .noneMatch(role -> "PROFESSOR".equals(role.getName()))) {
            throw new IllegalArgumentException("O usuário não tem a role PROFESSOR");
        }

        turma.setProfessor(professor);
        return turmaRepository.save(turma);
    }
    @Transactional // Adicione @Transactional para operações que modificam a entidade
    public void removerAlunoDeTurma(Long turmaId, UUID alunoId) {
        Turma turma = turmaRepository.findByIdWithAlunos(turmaId) 
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada."));

        User aluno = userRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        // Verifica se o aluno está realmente na turma
        boolean removed = turma.getAlunos().removeIf(a -> a.getId().equals(alunoId));

        if (!removed) {
            throw new IllegalArgumentException("Aluno não está matriculado nesta turma.");
        }

        turmaRepository.save(turma); // Salva a turma com o aluno removido
    }
}