package br.com.sarc.csw.modules.disciplina;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.disciplina.repository.DisciplinaRepository;
import br.com.sarc.csw.modules.disciplina.service.DisciplinaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    private Disciplina disciplina;

    @BeforeEach
    public void setup() {
        disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setNome("Matemática");
    }

    @Test
    public void testListarDisciplinas() {
        when(disciplinaRepository.findAll()).thenReturn(List.of(disciplina));

        List<Disciplina> result = disciplinaService.listarDisciplinas();
        
        assertEquals(1, result.size());
        assertEquals("Matemática", result.get(0).getNome());
    }

    @Test
    public void testGetDisciplina_Found() {
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        Optional<Disciplina> result = disciplinaService.getDisciplina(1L);
        
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    public void testGetDisciplina_NotFound() {
        when(disciplinaRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Disciplina> result = disciplinaService.getDisciplina(999L);
        
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSalvarDisciplina() {
        when(disciplinaRepository.save(any(Disciplina.class))).thenReturn(disciplina);

        Disciplina novaDisciplina = new Disciplina();
        novaDisciplina.setNome("Física");
        
        Disciplina result = disciplinaService.salvarDisciplina(novaDisciplina);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(disciplinaRepository).save(any(Disciplina.class));
    }

    @Test
    public void testAtualizarDisciplina_DisciplinaExistente() {
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(disciplinaRepository.save(any(Disciplina.class))).thenReturn(disciplina);

        Disciplina disciplinaAtualizada = new Disciplina();
        disciplinaAtualizada.setNome("Matemática Avançada");
        
        Disciplina result = disciplinaService.atualizarDisciplina(1L, disciplinaAtualizada);
        
        assertNotNull(result);
        verify(disciplinaRepository).save(any(Disciplina.class));
    }

    @Test
    public void testAtualizarDisciplina_DisciplinaNaoExistente() {
        when(disciplinaRepository.findById(999L)).thenReturn(Optional.empty());

        Disciplina disciplinaAtualizada = new Disciplina();
        disciplinaAtualizada.setNome("Matemática Avançada");
        
        Disciplina result = disciplinaService.atualizarDisciplina(999L, disciplinaAtualizada);
        
        assertNull(result);
        verify(disciplinaRepository, never()).save(any(Disciplina.class));
    }

    @Test
    public void testDeletarDisciplina_DisciplinaExistente() {
        when(disciplinaRepository.existsById(1L)).thenReturn(true);
        
        boolean result = disciplinaService.deletarDisciplina(1L);
        
        assertTrue(result);
        verify(disciplinaRepository).deleteById(1L);
    }

    @Test
    public void testDeletarDisciplina_DisciplinaNaoExistente() {
        when(disciplinaRepository.existsById(999L)).thenReturn(false);
        
        boolean result = disciplinaService.deletarDisciplina(999L);
        
        assertFalse(result);
        verify(disciplinaRepository, never()).deleteById(anyLong());
    }
}