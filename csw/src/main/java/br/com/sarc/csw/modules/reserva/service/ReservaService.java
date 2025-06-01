package br.com.sarc.csw.modules.reserva.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sarc.csw.core.enums.PeriodoAula;
import br.com.sarc.csw.core.enums.StatusRecurso;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.repository.RecursoRepository;
import br.com.sarc.csw.modules.reserva.model.Reserva;
import br.com.sarc.csw.modules.reserva.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    public Reserva obterPorId(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        return reserva.orElse(null);
    }

    public Reserva criar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(Long id, Reserva reservaAtualizada) {
        if (reservaRepository.existsById(id)) {
            reservaAtualizada.setId(id);
            return reservaRepository.save(reservaAtualizada);
        }
        return null;
    }
    public boolean recursoDisponivelParaAula(Long recursoId, String data, PeriodoAula periodo) {
        // Busca o recurso
        Recurso recurso = recursoRepository.findById(recursoId).orElse(null);
        if (recurso == null) {
            throw new IllegalArgumentException("Recurso não encontrado");
        }
        if (recurso.getStatus() == StatusRecurso.EM_MANUTENCAO) {
            throw new IllegalArgumentException("Recurso em manutenção");
        }
        
        

        // Busca todas as reservas desse recurso
        List<Reserva> reservas = reservaRepository.findAllByRecursoId(recursoId);
        for (Reserva reserva : reservas) {
            Aula aula = reserva.getAula();
            // Verifica se já existe reserva para o mesmo dia e período
            if (aula.getData().equals(data) && aula.getPeriodo() == periodo) {
                return false; // Conflito de horário
            }
        }
        return true; // Disponível
    }
    public boolean deletar(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}