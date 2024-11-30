package com.bancoDeDados.service;

import com.bancoDeDados.model.entities.Discente;
import com.bancoDeDados.model.entities.Pessoa;
import com.bancoDeDados.repository.DiscenteRepository;
import com.bancoDeDados.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscenteService {

    @Autowired
    private DiscenteRepository discenteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Discente salvar(Discente discente) {
        discenteRepository.salvar(discente);
        return discente;
    }

    public List<Discente> listarTodos() {
        List<Discente> discentes = discenteRepository.listarTodos();

        for (Discente discente : discentes) {
            Optional<Pessoa> optionalPessoa = pessoaRepository.buscarPorId(discente.getIdPessoa());
            optionalPessoa.ifPresent(pessoa -> discente.setPessoa(pessoa));
        }

        return discentes;
    }

    public Optional<Discente> buscarPorId(Long id) {
        return discenteRepository.buscarPorId(id);
    }

    public void deletar(Long id) {
        discenteRepository.deletar(id);
    }
}
