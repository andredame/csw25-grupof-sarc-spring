package br.com.sarc.csw.modules.sala.service;

import br.com.sarc.csw.modules.predio.repository.PredioRepository;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.sala.repository.SalaRepository;
import br.com.sarc.csw.modules.predio.model.Predio; // Import Predio

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Import Optional

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private PredioRepository predioRepository;

    // NOVO MÉTODO: Lista todas as salas
    public List<Sala> listarTodasSalas() {
        return salaRepository.findAllWithPredio(); // Assumindo que você tem este método no seu SalaRepository
    }

    public Sala criarSala(Long predioId, Sala sala) {
        Predio predio = predioRepository.findById(predioId)
            .orElseThrow(() -> new IllegalArgumentException("Prédio não encontrado com ID: " + predioId));
        sala.setPredio(predio);
        return salaRepository.save(sala);
    }

    // Método atualizado para atualizar por salaId diretamente
    public Sala atualizarSala(Long salaId, Sala salaAtualizada) {
        Sala salaExistente = salaRepository.findById(salaId)
            .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada com ID: " + salaId));

        // Atualiza apenas os campos permitidos
        salaExistente.setNome(salaAtualizada.getNome());
        salaExistente.setCapacidade(salaAtualizada.getCapacidade());
        salaExistente.setAndar(salaAtualizada.getAndar());
        // Não atualiza o predio, pois isso seria uma operação de mover sala, não de editar sala
        // Se precisar, crie um método separado para "mover sala para outro predio".

        return salaRepository.save(salaExistente);
    }

    public List<Sala> listarSalasPorPredio(Long predioId) {
        if (!predioRepository.existsById(predioId)) {
            throw new IllegalArgumentException("Prédio não encontrado com ID: " + predioId);
        }
        return salaRepository.findByPredioId(predioId);
    }

    public Sala buscarSalaPorPredioEId(Long predioId, Long salaId) {
        return salaRepository.findByIdAndPredioId(salaId, predioId)
            .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada no prédio especificado."));
    }

    // Método atualizado para deletar por salaId diretamente
    public void deletarSala(Long salaId) {
        if (!salaRepository.existsById(salaId)) {
            throw new IllegalArgumentException("Sala não encontrada com ID: " + salaId);
        }
        salaRepository.deleteById(salaId);
    }

    // Método antigo, para deletar sala de prédio específico (se ainda precisar)
    public void deletarSalaDePredio(Long predioId, Long salaId) {
        Sala sala = salaRepository.findByIdAndPredioId(salaId, predioId)
            .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada no prédio especificado para exclusão."));
        salaRepository.delete(sala);
    }


    public List<Sala> filtrarPorCapacidade(Long predioId, int capacidadeMinima) {
        if (!predioRepository.existsById(predioId)) {
            throw new IllegalArgumentException("Prédio não encontrado com ID: " + predioId);
        }
        return salaRepository.findByPredioIdAndCapacidadeGreaterThanEqual(predioId, capacidadeMinima);
    }
}