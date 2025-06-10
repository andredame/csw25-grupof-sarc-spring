package br.com.sarc.csw.modules.turma;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import br.com.sarc.csw.modules.turma.controller.TurmaController;
import br.com.sarc.csw.modules.turma.dto.TurmaDTO;
import br.com.sarc.csw.modules.turma.dto.TurmaMapper;
import br.com.sarc.csw.modules.turma.dto.TurmaResponseDTO;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.service.TurmaService;
import br.com.sarc.csw.modules.user.dto.AlunoResponseDTO;
import br.com.sarc.csw.modules.user.dto.UserMapper;
import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TurmaController.class)
@Import(SecurityConfig.class)
public class TurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TurmaService turmaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Turma turma;
    private TurmaDTO turmaDTO;

    @BeforeEach
    public void setup() {
        turma = new Turma();
        turma.setId(1L);
        turma.setNumero("TURMA-101");
        turma.setSemestre("2023/2");
        turma.setVagas(40);
        turma.setHorario("19:00 - 20:40");

        turmaDTO = TurmaMapper.toDTO(turma);
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testCriarTurma() throws Exception {
        when(turmaService.criarTurma(any(Turma.class))).thenReturn(turma);

        mockMvc.perform(post("/api/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(turmaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "professor", roles = {"PROFESSOR"})
    public void testListarTodasTurmas() throws Exception {
        when(turmaService.listarTodasTurmas()).thenReturn(List.of(turma));

        mockMvc.perform(get("/api/turmas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].numero").value("TURMA-101"));
    }

    @Test
    @WithMockUser(username = "professor", roles = {"PROFESSOR"})
    public void testObterTurma() throws Exception {
        when(turmaService.obterPorId(1L)).thenReturn(turma);

        mockMvc.perform(get("/api/turmas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "professor", roles = {"PROFESSOR"})
    public void testListarTurmasPorProfessor() throws Exception {
        UUID professorId = UUID.randomUUID();
        when(turmaService.listarTurmasPorProfessor(professorId)).thenReturn(List.of(turma));

        mockMvc.perform(get("/api/turmas/professor/" + professorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testAtualizarTurma() throws Exception {
        when(turmaService.atualizarTurma(eq(1L), any(Turma.class))).thenReturn(turma);

        mockMvc.perform(put("/api/turmas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(turmaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testDeletarTurma() throws Exception {
        mockMvc.perform(delete("/api/turmas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "professor", roles = {"PROFESSOR"})
    public void testListarAlunosPorTurma() throws Exception {
        User aluno = new User();
        aluno.setId(UUID.randomUUID());
        aluno.setUsername("joao.silva");
        aluno.setEmail("joao@example.com");

        when(turmaService.listarAlunosPorTurma(1L)).thenReturn(List.of(aluno));

        mockMvc.perform(get("/api/turmas/1/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("joao.silva"));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testVincularAlunoATurma() throws Exception {
        UUID alunoId = UUID.randomUUID();
        User aluno = new User();
        aluno.setId(alunoId);
        aluno.setUsername("joao.silva");
        aluno.setEmail("joao@example.com");

        when(turmaService.vincularAlunoATurma(1L, alunoId)).thenReturn(aluno);

        mockMvc.perform(post("/api/turmas/1/alunos/" + alunoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("joao.silva"));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testVincularProfessorATurma() throws Exception {
        UUID professorId = UUID.randomUUID();
        when(turmaService.vincularProfessorATurma(1L, professorId)).thenReturn(turma);

        mockMvc.perform(post("/api/turmas/1/professores/" + professorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}