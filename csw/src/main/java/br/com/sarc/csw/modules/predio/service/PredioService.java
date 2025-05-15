package br.com.sarc.csw.modules.predio.service;

import br.com.sarc.csw.modules.predio.model.Predio;
import br.com.sarc.csw.modules.predio.repository.PredioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PredioService {

    private final PredioRepository predioRepository;

    public List<Predio> listarTodos() {
        return predioRepository.findAll();
    }

    public Predio obterPorId(Long id) {
        return predioRepository.findById(id).orElse(null);
    }

    public Predio criar(Predio predio) {
        return predioRepository.save(predio);
    }

    public Predio atualizar(Long id, Predio predio) {
        if (predioRepository.existsById(id)) {
            predio.setId(id);
            return predioRepository.save(predio);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (predioRepository.existsById(id)) {
            predioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}