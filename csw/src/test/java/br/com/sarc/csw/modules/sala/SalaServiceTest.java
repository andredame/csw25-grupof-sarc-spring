// package br.com.sarc.csw.modules.sala;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// import java.util.List;
// import java.util.Optional;

// import br.com.sarc.csw.modules.predio.model.Predio;
// import br.com.sarc.csw.modules.predio.repository.PredioRepository;
// import br.com.sarc.csw.modules.sala.model.Sala;
// import br.com.sarc.csw.modules.sala.repository.SalaRepository;
// import br.com.sarc.csw.modules.sala.service.SalaService;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// @ExtendWith(MockitoExtension.class)
// public class SalaServiceTest {

//     @Mock
//     private SalaRepository salaRepository;

//     @Mock
//     private PredioRepository predioRepository;

//     @InjectMocks
//     private SalaService salaService;

//     private Sala sala;
//     private Predio predio;

//     @BeforeEach
//     public void setup() {
//         predio = new Predio();
//         predio.setId(1L);
//         predio.setNome("Prédio A");
        
//         sala = new Sala();
//         sala.setId(1L);
//         sala.setNome("Sala 101");
//         sala.setCapacidade(40);
//         sala.setAndar("1");
//         sala.setPredio(predio);
//     }

//     @Test
//     public void testAdicionarSala_PredioExistente() {
//         when(predioRepository.findById(1L)).thenReturn(Optional.of(predio));
//         when(salaRepository.save(any(Sala.class))).thenReturn(sala);

//         Sala novaSala = new Sala();
//         novaSala.setNome("Sala 102");
//         novaSala.setCapacidade(30);
        
//         Sala result = salaService.adicionarSala(1L, novaSala);
        
//         assertNotNull(result);
//         assertEquals(1L, result.getId());
//         assertEquals(predio, result.getPredio());
//         verify(salaRepository).save(any(Sala.class));
//     }

//     @Test
//     public void testAdicionarSala_PredioInexistente() {
//         when(predioRepository.findById(999L)).thenReturn(Optional.empty());

//         Sala novaSala = new Sala();
//         novaSala.setNome("Sala 102");
        
//         assertThrows(RuntimeException.class, () -> {
//             salaService.adicionarSala(999L, novaSala);
//         });
        
//         verify(salaRepository, never()).save(any(Sala.class));
//     }

 

//     @Test
//     public void testListarSalasPorPredio() {
//         when(salaRepository.findByPredioId(1L)).thenReturn(List.of(sala));

//         List<Sala> result = salaService.listarSalasPorPredio(1L);
        
//         assertEquals(1, result.size());
//         assertEquals(1L, result.get(0).getId());
//     }


   
//     @Test
//     public void testFiltrarPorCapacidade() {
//         when(salaRepository.findByPredioIdAndCapacidadeGreaterThanEqual(1L, 30)).thenReturn(List.of(sala));

//         List<Sala> result = salaService.filtrarPorCapacidade(1L, 30);
        
//         assertEquals(1, result.size());
//         assertEquals(40, result.get(0).getCapacidade());
//     }
// }