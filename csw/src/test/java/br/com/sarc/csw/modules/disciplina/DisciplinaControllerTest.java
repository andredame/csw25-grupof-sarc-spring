// package br.com.sarc.csw.modules.disciplina;


// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.List;
// import java.util.Optional;

// import br.com.sarc.csw.config.SecurityConfig;
// import br.com.sarc.csw.modules.disciplina.controller.DisciplinaController;
// import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
// import br.com.sarc.csw.modules.disciplina.dto.DisciplinaMapper;
// import br.com.sarc.csw.modules.disciplina.model.Disciplina;
// import br.com.sarc.csw.modules.disciplina.service.DisciplinaService;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;

// @WebMvcTest(DisciplinaController.class)
// @Import(SecurityConfig.class)
// public class DisciplinaControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private DisciplinaService disciplinaService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     private Disciplina disciplina;
//     private DisciplinaDTO disciplinaDTO;

//     @BeforeEach
//     public void setup() {
//         disciplina = new Disciplina();
//         disciplina.setId(1L);
//         disciplina.setNome("Matemática");

//         disciplinaDTO = DisciplinaMapper.toDTO(disciplina);
//     }

//     @Test
//     @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
//     public void testListarDisciplinas() throws Exception {
//         when(disciplinaService.listarDisciplinas()).thenReturn(List.of(disciplina));

//         mockMvc.perform(get("/api/disciplinas"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$[0].id").value(1L))
//                 .andExpect(jsonPath("$[0].nome").value("Matemática"));
//     }

//     @Test
//     @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
//     public void testObterDisciplina_Found() throws Exception {
//         when(disciplinaService.getDisciplina(1L)).thenReturn(Optional.of(disciplina));

//         mockMvc.perform(get("/api/disciplinas/1"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 .andExpect(jsonPath("$.nome").value("Matemática"));
//     }

//     // @Test
//     // @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
//     // public void testCriarDisciplina() throws Exception {
//     //     when(disciplinaService.salvarDisciplina(any(Disciplina.class))).thenReturn(disciplina);

//     //     mockMvc.perform(post("/api/disciplinas")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(disciplinaDTO)))
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$.id").value(1L))
//     //             .andExpect(jsonPath("$.nome").value("Matemática"));
//     // }

//     // @Test
//     // @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
//     // public void testAtualizarDisciplina() throws Exception {
//     //     when(disciplinaService.atualizarDisciplina(eq(1L), any(Disciplina.class))).thenReturn(disciplina);

//     //     mockMvc.perform(put("/api/disciplinas/1")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content(objectMapper.writeValueAsString(disciplinaDTO)))
//     //             .andExpect(status().isOk())
//     //             .andExpect(jsonPath("$.id").value(1L));
//     // }

//     @Test
//     @WithMockUser(username = "coordenador", roles = {"COORDENADOR"})
//     public void testDeletarDisciplina() throws Exception {
//         when(disciplinaService.deletarDisciplina(1L)).thenReturn(true);

//         mockMvc.perform(delete("/api/disciplinas/1"))
//                 .andExpect(status().isNoContent());
//     }
// }
