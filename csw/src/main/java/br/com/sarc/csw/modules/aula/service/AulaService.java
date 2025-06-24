package br.com.sarc.csw.modules.aula.service;

import br.com.sarc.csw.core.enums.PeriodoAula;
import br.com.sarc.csw.modules.aula.model.Aula;
import br.com.sarc.csw.modules.aula.repository.AulaRepository;
import br.com.sarc.csw.modules.sala.model.Sala;
import br.com.sarc.csw.modules.sala.repository.SalaRepository;
import br.com.sarc.csw.modules.turma.model.Turma;
import br.com.sarc.csw.modules.turma.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate; // Import LocalDate
import java.util.List;
import java.util.UUID;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private SalaRepository salaRepository; // Inject SalaRepository

    public List<Aula> listarAulasPorTurmaEProfessor(Long turmaId, UUID professorId) {
        return aulaRepository.findByTurmaIdAndTurmaProfessorId(turmaId, professorId);
    }
    public List<Aula> listarAulasPorTurma(Long turmaId) {
        return aulaRepository.findByTurmaId(turmaId);
    }

    public Aula criarAula(Long turmaId, Long salaId, LocalDate data, PeriodoAula periodo, String descricao, UUID professorId) { // Updated method signature
        // 1. Verify if the Turma exists and belongs to the professor
        Turma turma = turmaRepository.findById(turmaId).orElseThrow(() -> new RuntimeException("Turma não encontrada."));
        if (!turma.getProfessor().getId().equals(professorId)) {
            throw new RuntimeException("Turma não pertence ao professor.");
        }

        // 2. Verify if the Sala exists
        Sala sala = salaRepository.findById(salaId).orElseThrow(() -> new RuntimeException("Sala não encontrada."));

        // 3. Check Sala availability for the given date and period
        if (!isSalaAvailable(salaId, data, periodo)) {
            throw new RuntimeException("Sala já reservada para outra aula no mesmo dia e período.");
        }

        // 4. Check capacity: Turma vagas vs. Sala capacidade
        if (turma.getVagas() != null && sala.getCapacidade() != null && turma.getVagas() > sala.getCapacidade()) {
            throw new RuntimeException("A capacidade da sala é insuficiente para o número de vagas da turma.");
        }

        Aula aula = new Aula();
        aula.setTurma(turma);
        aula.setSala(sala);
        aula.setData(data);
        aula.setPeriodo(periodo);
        aula.setDescricao(descricao);

        return aulaRepository.save(aula);
    }

    public List<Aula> listarAulasPorData(java.time.LocalDate data) {
        return aulaRepository.findByDataWithDetails(data);
    }

    public Aula atualizarAula(Long aulaId, Aula aulaAtualizada) {
        // Implement comprehensive update logic similar to create, checking for conflicts, and associations
        Aula existingAula = aulaRepository.findById(aulaId).orElse(null);
        if (existingAula == null) {
            return null; // Or throw an exception if Aula not found
        }

        // Ensure associated Turma and Sala are valid if they are being changed
        if (aulaAtualizada.getTurma() != null && !turmaRepository.existsById(aulaAtualizada.getTurma().getId())) {
            throw new RuntimeException("Turma associada à aula não encontrada.");
        }
        if (aulaAtualizada.getSala() != null && !salaRepository.existsById(aulaAtualizada.getSala().getId())) {
            throw new RuntimeException("Sala associada à aula não encontrada.");
        }

        // Check for Sala availability if sala, data, or periodo are being updated
        if (aulaAtualizada.getSala() != null && aulaAtualizada.getData() != null && aulaAtualizada.getPeriodo() != null &&
            (!existingAula.getSala().equals(aulaAtualizada.getSala()) ||
             !existingAula.getData().equals(aulaAtualizada.getData()) ||
             !existingAula.getPeriodo().equals(aulaAtualizada.getPeriodo()))) {

            if (!isSalaAvailable(aulaAtualizada.getSala().getId(), aulaAtualizada.getData(), aulaAtualizada.getPeriodo())) {
                throw new RuntimeException("Sala já reservada para outra aula no mesmo dia e período.");
            }
        }
        
        // Check capacity if turma or sala are being updated
        Turma updatedTurma = aulaAtualizada.getTurma() != null ? aulaAtualizada.getTurma() : existingAula.getTurma();
        Sala updatedSala = aulaAtualizada.getSala() != null ? aulaAtualizada.getSala() : existingAula.getSala();
        if (updatedTurma.getVagas() != null && updatedSala.getCapacidade() != null && updatedTurma.getVagas() > updatedSala.getCapacidade()) {
            throw new RuntimeException("A capacidade da sala é insuficiente para o número de vagas da turma.");
        }


        aulaAtualizada.setId(aulaId);
        return aulaRepository.save(aulaAtualizada);
    }

    public Aula obterPorId(Long aulaId) {
        return aulaRepository.findById(aulaId).orElse(null);
    }

    public boolean deletarAula(Long aulaId) {
        if (aulaRepository.existsById(aulaId)) {
            aulaRepository.deleteById(aulaId);
            return true;
        }
        return false;
    }
    public Aula salvar(Aula aula) {
        // This 'salvar' method is redundant with 'criarAula' and 'atualizarAula' if the logic is central.
        // Consider removing or making it private if 'criarAula' and 'atualizarAula' are the public entry points.
        return aulaRepository.save(aula);
    }
    public List<Aula> buscarTodas() {
        // Altere esta linha para usar o novo método do repositório
        return aulaRepository.findAllWithDetails();
    }

    public Aula buscarPorId(Long id) {
        return aulaRepository.findById(id).orElse(null);
    }
    public Aula atualizar(Long id, Aula aulaAtualizada) {
        // This 'atualizar' method is redundant with 'atualizarAula'.
        // Consider removing or making it private.
        if (aulaRepository.existsById(id)) {
            aulaAtualizada.setId(id);
            return aulaRepository.save(aulaAtualizada);
        }
        return null;
    }
    public void deletar(Long id) {
        aulaRepository.deleteById(id);
    }

    public List<Aula> listarAulasPorProfessor(UUID professorId) {
        return aulaRepository.findByTurmaProfessorId(professorId);
    }

    // New method to check if a sala is available for a given date and period
    private boolean isSalaAvailable(Long salaId, LocalDate data, PeriodoAula periodo) {
        return !aulaRepository.existsBySalaIdAndDataAndPeriodo(salaId, data, periodo);
    }
}