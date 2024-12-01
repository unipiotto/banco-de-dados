package com.bancoDeDados.service;

import com.bancoDeDados.model.Professor;
import com.bancoDeDados.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public Professor salvar(Professor professor) {
        professorRepository.salvar(professor);
        return professor;
    }

    public Optional<Professor> buscarPorId(Long id) {
        return professorRepository.buscarPorId(id);
    }

    public void deletar(Long id) {
        professorRepository.deletar(id);
    }
}
