package br.com.sarc.csw.modules.disciplina.service;

import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.disciplina.repository.DisciplinaRepository;
import br.com.sarc.csw.modules.disciplina.dto.DisciplinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> getDisciplina(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Disciplina salvarDisciplina(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina atualizarDisciplina(Long id, Disciplina disciplinaAtualizada) {
        if (disciplinaRepository.existsById(id)) {
            disciplinaAtualizada.setId(id);
            return disciplinaRepository.save(disciplinaAtualizada);
        }
        return null;
    }

    public boolean deletarDisciplina(Long id) {
        if (disciplinaRepository.existsById(id)) {
            disciplinaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}