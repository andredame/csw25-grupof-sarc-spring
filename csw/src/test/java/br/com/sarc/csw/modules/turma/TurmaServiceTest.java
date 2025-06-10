package br.com.sarc.csw.modules.turma;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;
import br.com.sarc.csw.modules.turma.service.TurmaService;
import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class TurmaServiceTest {

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TurmaService turmaService;

    private Turma turma;
    private User aluno;
    private User professor;

    @BeforeEach
    public void setup() {
        turma = new Turma();
        turma.setId(1L);
        turma.setNumero("TURMA-101");
        turma.setSemestre("2023/2");
        turma.setVagas(40);
        turma.setAlunos(new ArrayList<>());

        aluno = new User();
        aluno.setId(UUID.randomUUID());
        aluno.setUsername("joao.silva");

        professor = new User();
        professor.setId(UUID.randomUUID());
        professor.setUsername("professor.silva");
    }

    @Test
    public void testListarTodasTurmas() {
        when(turmaRepository.findAll()).thenReturn(List.of(turma));

        List<Turma> result = turmaService.listarTodasTurmas();

        assertEquals(1, result.size());
        assertEquals("TURMA-101", result.get(0).getNumero());
    }

    @Test
    public void testObterPorId_Existente() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));

        Turma result = turmaService.obterPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testObterPorId_NaoExistente() {
        when(turmaRepository.findById(999L)).thenReturn(Optional.empty());

        Turma result = turmaService.obterPorId(999L);

        assertNull(result);
    }

    @Test
    public void testListarTurmasPorProfessor() {
        UUID professorId = professor.getId();
        when(turmaRepository.findByProfessorId(professorId)).thenReturn(List.of(turma));

        List<Turma> result = turmaService.listarTurmasPorProfessor(professorId);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testCriarTurma() {
        when(turmaRepository.save(any(Turma.class))).thenReturn(turma);

        Turma novaTurma = new Turma();
        novaTurma.setNumero("TURMA-NEW");
        Turma result = turmaService.criarTurma(novaTurma);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(turmaRepository).save(any(Turma.class));
    }

    @Test
    public void testAtualizarTurma_Existente() {
        when(turmaRepository.existsById(1L)).thenReturn(true);
        when(turmaRepository.save(any(Turma.class))).thenReturn(turma);

        Turma turmaAtualizada = new Turma();
        turmaAtualizada.setId(1L);
        turmaAtualizada.setNumero("TURMA-UPDATED");
        Turma result = turmaService.atualizarTurma(1L, turmaAtualizada);

        assertNotNull(result);
        verify(turmaRepository).save(any(Turma.class));
    }

    @Test
    public void testAtualizarTurma_NaoExistente() {
        when(turmaRepository.existsById(999L)).thenReturn(false);

        Turma turmaAtualizada = new Turma();
        turmaAtualizada.setNumero("TURMA-UPDATED");
        Turma result = turmaService.atualizarTurma(999L, turmaAtualizada);

        assertNull(result);
        verify(turmaRepository, never()).save(any(Turma.class));
    }

    @Test
    public void testDeletarTurma() {
        turmaService.deletarTurma(1L);
        verify(turmaRepository).deleteById(1L);
    }

    @Test
    public void testVincularAlunoATurma_TurmaEAlunoExistem() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(userRepository.findById(aluno.getId())).thenReturn(Optional.of(aluno));
        when(turmaRepository.save(any(Turma.class))).thenReturn(turma);

        User result = turmaService.vincularAlunoATurma(1L, aluno.getId());

        assertNotNull(result);
        verify(turmaRepository).save(any(Turma.class));
    }

    @Test
    public void testVincularAlunoATurma_TurmaNaoExiste() {
        when(turmaRepository.findById(999L)).thenReturn(Optional.empty());

        User result = turmaService.vincularAlunoATurma(999L, aluno.getId());

        assertNull(result);
        verify(turmaRepository, never()).save(any(Turma.class));
    }

    @Test
    public void testVincularAlunoATurma_AlunoNaoExiste() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        User result = turmaService.vincularAlunoATurma(1L, UUID.randomUUID());

        assertNull(result);
        verify(turmaRepository, never()).save(any(Turma.class));
    }

    @Test
    public void testVincularProfessorATurma_TurmaEProfessorExistem() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        when(userRepository.findById(professor.getId())).thenReturn(Optional.of(professor));
        when(turmaRepository.save(any(Turma.class))).thenReturn(turma);

        Turma result = turmaService.vincularProfessorATurma(1L, professor.getId());

        assertNotNull(result);
        verify(turmaRepository).save(any(Turma.class));
    }
}