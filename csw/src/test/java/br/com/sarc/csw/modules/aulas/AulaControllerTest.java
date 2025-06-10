package br.com.sarc.csw.modules.aulas;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import br.com.sarc.csw.modules.aula.dto.AulaRequestDTO;
import br.com.sarc.csw.modules.aula.dto.AulaResponseDTO;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.aula.service.AulaService;
import br.com.sarc.csw.modules.turma.model.Turma;
import lombok.With;
import br.com.sarc.csw.config.SecurityConfig;
import br.com.sarc.csw.core.enums.PeriodoAula;
import br.com.sarc.csw.modules.aula.controller.AulaController;
import br.com.sarc.csw.modules.aula.dto.AulaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(AulaController.class)
@Import({SecurityConfig.class})  // sua classe de configuração real de segurança
// @AutoConfigureMockMvc(addFilters = false)  
@WithMockUser(username = "professor", roles = {"PROFESSOR"})
public class AulaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AulaService aulaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Aula aula;
    private AulaResponseDTO aulaResponseDTO;
    private AulaRequestDTO aulaRequestDTO;

    @BeforeEach
    public void setup() {
        // Criar uma turma válida
        Turma turma = new Turma();
        turma.setId(1L);
        // outros atributos da turma...

        // Criar a aula como variável de instância (não local)
        aula = new Aula();
        aula.setId(1L);
        aula.setTurma(turma);
        aula.setData("2023-10-01");
        // Adicione outros atributos necessários aqui
        
        // Inicializar o aulaResponseDTO
        aulaResponseDTO = AulaMapper.toResponseDTO(aula);
        
        // Inicializar o aulaRequestDTO
        aulaRequestDTO = new AulaRequestDTO();
        aulaRequestDTO.setData("2023-10-01");
        aulaRequestDTO.setTurmaId(1L);
        aulaRequestDTO.setSalaId(1L);
        aulaRequestDTO.setDisciplinaId(1L);
        aulaRequestDTO.setPeriodo(PeriodoAula.JK);
    }

    @Test
    public void testCriarAula() throws Exception {
        when(aulaService.salvar(any(Aula.class))).thenReturn(aula);

        mockMvc.perform(post("/api/aulas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aulaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(aula.getId()));
    }

    @Test
    public void testListarAulasPorProfessor() throws Exception {
        UUID professorId = UUID.randomUUID();

        when(aulaService.listarAulasPorProfessor(professorId))
            .thenReturn(List.of(aula));

        mockMvc.perform(get("/api/aulas/professor/" + professorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(aula.getId()));
    }

    @Test
    public void testBuscarAulaPorId_Found() throws Exception {
        when(aulaService.buscarPorId(1L)).thenReturn(aula);

        mockMvc.perform(get("/api/aulas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testBuscarAulaPorId_NotFound() throws Exception {
        when(aulaService.buscarPorId(1L)).thenReturn(null);

        mockMvc.perform(get("/api/aulas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAtualizarAula_Found() throws Exception {
        when(aulaService.buscarPorId(1L)).thenReturn(aula);
        when(aulaService.salvar(any(Aula.class))).thenReturn(aula);

        mockMvc.perform(put("/api/aulas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aulaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAtualizarAula_NotFound() throws Exception {
        when(aulaService.buscarPorId(1L)).thenReturn(null);

        mockMvc.perform(put("/api/aulas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aulaRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletarAula_Found() throws Exception {
        when(aulaService.buscarPorId(1L)).thenReturn(aula);

        mockMvc.perform(delete("/api/aulas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletarAula_NotFound() throws Exception {
        when(aulaService.buscarPorId(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/aulas/1"))
                .andExpect(status().isNotFound());
    }

}
