package br.com.sarc.csw.modules.predio;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import br.com.sarc.csw.config.SecurityConfig;
import br.com.sarc.csw.modules.predio.controller.PredioController;
import br.com.sarc.csw.modules.predio.dto.PredioDTO;
import br.com.sarc.csw.modules.predio.dto.PredioMapper;
import br.com.sarc.csw.modules.predio.model.Predio;
import br.com.sarc.csw.modules.predio.service.PredioService;
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

@WebMvcTest(PredioController.class)
@Import(SecurityConfig.class)
public class PredioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PredioService predioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Predio predio;
    private PredioDTO predioDTO;

    @BeforeEach
    public void setup() {
        predio = new Predio();
        predio.setId(1L);
        predio.setNome("Prédio A");
        predio.setRua("Rua 1");
        predio.setNumero("123");
        predio.setComplemento("Apto 1");
        predio.setBairro("Bairro A");
        predio.setCidade("Cidade A");
        predio.setUf("UF");
        predio.setCep("12345-678");
        


        predioDTO = PredioMapper.toDTO(predio);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testListarTodos() throws Exception {
        when(predioService.listarTodos()).thenReturn(List.of(predio));

        mockMvc.perform(get("/api/predios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Prédio A"));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testListarTodos_ComoCoordenador() throws Exception {
        when(predioService.listarTodos()).thenReturn(List.of(predio));

        mockMvc.perform(get("/api/predios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Prédio A"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testObterPorId_Found() throws Exception {
        when(predioService.obterPorId(1L)).thenReturn(predio);

        mockMvc.perform(get("/api/predios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Prédio A"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testObterPorId_NotFound() throws Exception {
        when(predioService.obterPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/predios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testCriar() throws Exception {
        when(predioService.criar(any(Predio.class))).thenReturn(predio);

        mockMvc.perform(post("/api/predios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(predioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Prédio A"));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testAtualizar_Found() throws Exception {
        when(predioService.atualizar(eq(1L), any(Predio.class))).thenReturn(predio);

        mockMvc.perform(put("/api/predios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(predioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testAtualizar_NotFound() throws Exception {
        when(predioService.atualizar(eq(999L), any(Predio.class))).thenReturn(null);

        mockMvc.perform(put("/api/predios/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(predioDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testDeletar_Success() throws Exception {
        when(predioService.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/predios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
    public void testDeletar_NotFound() throws Exception {
        when(predioService.deletar(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/predios/999"))
                .andExpect(status().isNotFound());
    }
}