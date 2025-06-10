package br.com.sarc.csw.modules.recurso;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;

import br.com.sarc.csw.config.SecurityConfig;
import br.com.sarc.csw.core.enums.StatusRecurso;
import br.com.sarc.csw.modules.recurso.controller.RecursoController;
import br.com.sarc.csw.modules.recurso.dto.RecursoDTO;
import br.com.sarc.csw.modules.recurso.dto.RecursoMapper;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.model.TipoRecurso;
import br.com.sarc.csw.modules.recurso.service.RecursoService;
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

@WebMvcTest(RecursoController.class)
@Import(SecurityConfig.class)
public class RecursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecursoService recursoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Recurso recurso;
    private RecursoDTO recursoDTO;

    @BeforeEach
    public void setup() {
        // Criar TipoRecurso
        TipoRecurso tipoRecurso = new TipoRecurso();
        tipoRecurso.setId(1L);
        tipoRecurso.setNome("Tecnológico");
        
        // Criar Recurso completo
        recurso = new Recurso();
        recurso.setId(1L);
        recurso.setStatus(StatusRecurso.DISPONIVEL);
        recurso.setTipoRecurso(tipoRecurso);

        // Converter para DTO
        recursoDTO = RecursoMapper.toDTO(recurso);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testListarRecursos() throws Exception {
        when(recursoService.listarTodos()).thenReturn(List.of(recurso));

        mockMvc.perform(get("/api/recursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].tipoRecurso.nome").value("Tecnológico"));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testListarRecursos_ComoCoordenador() throws Exception {
        when(recursoService.listarTodos()).thenReturn(List.of(recurso));

        mockMvc.perform(get("/api/recursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testObterRecurso_Found() throws Exception {
        when(recursoService.obterPorId(1L)).thenReturn(recurso);

        mockMvc.perform(get("/api/recursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipoRecurso.nome").value("Tecnológico"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testObterRecurso_NotFound() throws Exception {
        when(recursoService.obterPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/recursos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testCriarRecurso() throws Exception {
        when(recursoService.criar(any(Recurso.class))).thenReturn(recurso);

        mockMvc.perform(post("/api/recursos")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recursoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testAtualizarRecurso_Found() throws Exception {
        when(recursoService.atualizar(eq(1L), any(Recurso.class))).thenReturn(recurso);

        mockMvc.perform(put("/api/recursos/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recursoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testDeletarRecurso() throws Exception {
        when(recursoService.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/recursos/1")
                .with(csrf()))
                .andExpect(status().isNoContent());
    }
}