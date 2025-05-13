package br.com.sarc.csw.modules.sala.service;

import br.com.sarc.csw.modules.predio.model.Predio;
import br.com.sarc.csw.modules.predio.repository.PredioRepository;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.sala.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private PredioRepository predioRepository;

    public Sala adicionarSala(Long predioId, Sala sala) {
        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new RuntimeException("Prédio não encontrado"));
        sala.setPredio(predio);
        return salaRepository.save(sala);
    }

    public Sala editarSala(Long predioId, Long salaId, Sala salaAtualizada) {
        Sala sala = salaRepository.findById(salaId).orElse(null);
        if (sala != null && sala.getPredio().getId().equals(predioId)) {
            sala.setNome(salaAtualizada.getNome());
            sala.setCapacidade(salaAtualizada.getCapacidade());
            sala.setAndar(salaAtualizada.getAndar());
            return salaRepository.save(sala);
        }
        return null;
    }

    public List<Sala> listarSalasPorPredio(Long predioId) {
        return salaRepository.findByPredioId(predioId);
    }

    public Sala buscarSalaPorId(Long predioId, Long salaId) {
        Sala sala = salaRepository.findById(salaId).orElse(null);
        if (sala != null && sala.getPredio().getId().equals(predioId)) {
            return sala;
        }
        return null;
    }

    public boolean deletarSala(Long predioId, Long salaId) {
        Sala sala = salaRepository.findById(salaId).orElse(null);
        if (sala != null && sala.getPredio().getId().equals(predioId)) {
            salaRepository.delete(sala);
            return true;
        }
        return false;
    }

    public List<Sala> filtrarPorCapacidade(Long predioId, int capacidadeMinima) {
        return salaRepository.findByPredioIdAndCapacidadeGreaterThanEqual(predioId, capacidadeMinima);
    }
}
