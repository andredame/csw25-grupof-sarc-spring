package br.com.sarc.csw.modules.disciplina.service;

import br.com.sarc.csw.modules.disciplina.model.Disciplina;
import br.com.sarc.csw.modules.disciplina.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> obterPorId(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Disciplina criarDisciplina(Disciplina disciplina) {
        // Validação: Checar se já existe uma disciplina com o mesmo código
        if (disciplinaRepository.findByCodigo(disciplina.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma disciplina com o código: " + disciplina.getCodigo());
        }
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina atualizarDisciplina(Long id, Disciplina disciplinaAtualizada) {
        return disciplinaRepository.findById(id).map(disciplinaExistente -> {
            // Validação: Se o código foi alterado, checar unicidade
            if (!disciplinaAtualizada.getCodigo().equals(disciplinaExistente.getCodigo())) {
                if (disciplinaRepository.findByCodigo(disciplinaAtualizada.getCodigo()).isPresent()) {
                    throw new IllegalArgumentException("Já existe outra disciplina com o código: " + disciplinaAtualizada.getCodigo());
                }
            }
            disciplinaExistente.setCodigo(disciplinaAtualizada.getCodigo());
            disciplinaExistente.setNome(disciplinaAtualizada.getNome());
            disciplinaExistente.setCreditos(disciplinaAtualizada.getCreditos());
            disciplinaExistente.setSemestre(disciplinaAtualizada.getSemestre());
            disciplinaExistente.setPrograma(disciplinaAtualizada.getPrograma());
            return disciplinaRepository.save(disciplinaExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada com o ID: " + id));
    }

    public boolean deletarDisciplina(Long id) {
        if (disciplinaRepository.existsById(id)) {
            disciplinaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}