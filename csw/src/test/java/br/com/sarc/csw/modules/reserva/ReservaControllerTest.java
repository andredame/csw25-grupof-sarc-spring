// package br.com.sarc.csw.modules.reserva;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import br.com.sarc.csw.config.SecurityConfig;
// import br.com.sarc.csw.modules.aula.model.Aula;
// import br.com.sarc.csw.modules.aula.service.AulaService;
// import br.com.sarc.csw.modules.recurso.model.Recurso;
// import br.com.sarc.csw.modules.recurso.service.RecursoService;
// import br.com.sarc.csw.modules.reserva.controller.ReservaController;
// import br.com.sarc.csw.modules.reserva.dto.ReservaDTO;
// import br.com.sarc.csw.modules.reserva.dto.ReservaResponseDTO;
// import br.com.sarc.csw.modules.reserva.model.Reserva;
// import br.com.sarc.csw.modules.reserva.service.ReservaService;

// @WebMvcTest(ReservaController.class)
// @Import(SecurityConfig.class)
// public class ReservaControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private ReservaService reservaService;

//     @MockBean
//     private AulaService aulaService;

//     @MockBean
//     private RecursoService recursoService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     private Reserva reserva;
//     private ReservaDTO reservaDTO;
//     private Aula aula;
//     private Recurso recurso;

//     @BeforeEach
//     void setup() {
//         // Configuração inicial dos objetos
//         aula = new Aula();
//         aula.setId(10L);
//         aula.setData("2023-10-15");

//         recurso = new Recurso();
//         recurso.setId(5L);

//         reserva = new Reserva();
//         reserva.setId(1L);
//         reserva.setAula(aula);
//         reserva.setRecurso(recurso);

//         reservaDTO = new ReservaDTO();
//         reservaDTO.setId_aula(10L);
//         reservaDTO.setId_recurso(5L);
//     }

 

//     @Test
//     @WithMockUser(username = "professor@teste.com", roles = {"PROFESSOR"})
//     void testDeletarReserva_Sucesso() throws Exception {
//         when(reservaService.deletar(1L)).thenReturn(true);

//         mockMvc.perform(delete("/api/reservas/1")
//                 .with(csrf()))
//                 .andExpect(status().isNoContent());
//     }

 
// }