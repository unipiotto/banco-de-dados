package com.bancoDeDados.service;

import com.bancoDeDados.model.entities.Discente;
import com.bancoDeDados.model.entities.Pessoa;
import com.bancoDeDados.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa salvar(Pessoa pessoa) {
        pessoaRepository.salvar(pessoa);
        return pessoa;
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.buscarPorId(id);
    }

    public void deletar(Long id) {
        pessoaRepository.deletar(id);
    }
}
