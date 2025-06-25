package br.com.sarc.csw.modules.reserva.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.sarc.csw.modules.aula.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sarc.csw.core.enums.PeriodoAula;
import br.com.sarc.csw.core.enums.StatusRecurso;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.recurso.model.Recurso;
import br.com.sarc.csw.modules.recurso.repository.RecursoRepository;
import br.com.sarc.csw.modules.reserva.model.Reserva;
import br.com.sarc.csw.modules.reserva.repository.ReservaRepository;
import br.com.sarc.csw.modules.sala.repository.SalaRepository;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private AulaRepository aulaRepository; // Inject AulaRepository

     public List<Reserva> listarTodasReservas() {
        return reservaRepository.findAllWithDetails(); // Alterado para usar a query otimizada
    }

    public List<Reserva> listarReservasPorProfessor(UUID professorId) {
        return reservaRepository.findAllByAulaProfessorIdWithDetails(professorId);
    }

    public Reserva obterPorId(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        return reserva.orElse(null);
    }

    public Reserva criar(Reserva reserva) {
        
        if (reserva.getAula() == null || !aulaRepository.existsById(reserva.getAula().getId())) {
            throw new IllegalArgumentException("Aula associada à reserva não encontrada.");
        }
        if (reserva.getRecurso() == null || !recursoRepository.existsById(reserva.getRecurso().getId())) {
            throw new IllegalArgumentException("Recurso associado à reserva não encontrado.");
        }
        if (reservaRepository.existsByAulaIdAndRecursoId(reserva.getAula().getId(), reserva.getRecurso().getId())) {
            throw new IllegalArgumentException("Já existe uma reserva para esta aula e recurso.");
        }
        
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(Long id, Reserva reservaAtualizada) {
        if (reservaRepository.existsById(id)) {
            // Before updating a reservation, similar checks as create might be needed depending on the update type
            if (reservaAtualizada.getAula() == null || !aulaRepository.existsById(reservaAtualizada.getAula().getId())) {
                throw new IllegalArgumentException("Aula associada à reserva não encontrada.");
            }
            reservaAtualizada.setId(id);
            return reservaRepository.save(reservaAtualizada);
        }
        return null;
    }

    public boolean recursoDisponivelParaAula(Long recursoId, LocalDate data, PeriodoAula periodo) { 
        Recurso recurso = recursoRepository.findById(recursoId).orElse(null);
        if (recurso == null) {
            throw new IllegalArgumentException("Recurso não encontrado");
        }
        if (recurso.getStatus() == StatusRecurso.EM_MANUTENCAO) {
            throw new IllegalArgumentException("Recurso em manutenção");
        }

        // Busca todas as reservas desse recurso
        List<Reserva> reservas = reservaRepository.findAllWithDetailsByRecursoId(recursoId);
        for (Reserva reserva : reservas) {
            Aula aula = reserva.getAula();
            // Verifica se já existe reserva para o mesmo dia e período
            // Ensure aula is not null and has a valid data/periodo
            if (aula != null && aula.getData() != null && aula.getPeriodo() != null &&
                aula.getData().equals(data) && aula.getPeriodo() == periodo) {
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