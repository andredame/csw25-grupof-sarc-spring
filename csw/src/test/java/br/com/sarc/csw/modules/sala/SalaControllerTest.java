package br.com.sarc.csw.modules.sala;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import br.com.sarc.csw.config.SecurityConfig;
import br.com.sarc.csw.modules.predio.model.Predio;
import br.com.sarc.csw.modules.sala.controller.SalaController;
import br.com.sarc.csw.modules.sala.dto.SalaMapper;
import br.com.sarc.csw.modules.sala.dto.SalaRequestDTO;
import br.com.sarc.csw.modules.sala.dto.SalaResponseDTO;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.sala.service.SalaService;
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

@WebMvcTest(SalaController.class)
@Import(SecurityConfig.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class SalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaService salaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Sala sala;
    private SalaRequestDTO salaRequestDTO;
    private Predio predio;

    @BeforeEach
    public void setup() {
        predio = new Predio();
        predio.setId(1L);
        predio.setNome("Prédio A");
        
        sala = new Sala();
        sala.setId(1L);
        sala.setNome("Sala 101");
        sala.setCapacidade(40);
        sala.setAndar("1");
        sala.setPredio(predio);

        salaRequestDTO = new SalaRequestDTO();
        salaRequestDTO.setNome("Sala 101");
        salaRequestDTO.setCapacidade(40);
        salaRequestDTO.setAndar("1");
    }

    @Test
    public void testAdicionarSala() throws Exception {
        when(salaService.adicionarSala(eq(1L), any(Sala.class))).thenReturn(sala);

        mockMvc.perform(post("/api/predios/1/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Sala 101"));
    }

    @Test
    public void testEditarSala() throws Exception {
        when(salaService.editarSala(eq(1L), eq(1L), any(Sala.class))).thenReturn(sala);

        mockMvc.perform(put("/api/predios/1/salas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testEditarSala_NotFound() throws Exception {
        when(salaService.editarSala(eq(1L), eq(999L), any(Sala.class))).thenReturn(null);

        mockMvc.perform(put("/api/predios/1/salas/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salaRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testListarSalas() throws Exception {
        when(salaService.listarSalasPorPredio(1L)).thenReturn(List.of(sala));

        mockMvc.perform(get("/api/predios/1/salas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Sala 101"));
    }

    @Test
    public void testBuscarSalaPorId_Found() throws Exception {
        when(salaService.buscarSalaPorId(1L, 1L)).thenReturn(sala);

        mockMvc.perform(get("/api/predios/1/salas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Sala 101"));
    }

    @Test
    public void testBuscarSalaPorId_NotFound() throws Exception {
        when(salaService.buscarSalaPorId(1L, 999L)).thenReturn(null);

        mockMvc.perform(get("/api/predios/1/salas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletarSala_Success() throws Exception {
        when(salaService.deletarSala(1L, 1L)).thenReturn(true);
        when(salaService.buscarSalaPorId(1L, 1L)).thenReturn(sala);

        mockMvc.perform(delete("/api/predios/1/salas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletarSala_NotFound() throws Exception {
        when(salaService.buscarSalaPorId(1L, 999L)).thenReturn(null);

        mockMvc.perform(delete("/api/predios/1/salas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFiltrarSalasPorCapacidade() throws Exception {
        when(salaService.filtrarPorCapacidade(1L, 30)).thenReturn(List.of(sala));

        mockMvc.perform(get("/api/predios/1/salas/filtro")
                .param("capacidadeMinima", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].capacidade").value(40));
    }
}