package com.bancoDeDados.service;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.repository.dao.DiscenteDAO;
import com.bancoDeDados.repository.dao.PessoaDAO;
import com.bancoDeDados.repository.DiscenteRepository;
import com.bancoDeDados.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DiscenteService {

    private final PessoaDAO pessoaDAO;
    private final DiscenteDAO discenteDAO;
    private final DiscenteRepository discenteRepository;
    private final PessoaRepository pessoaRepository;
    private final AtomicInteger contador;

    @Autowired
    public DiscenteService(PessoaDAO pessoaDAO, DiscenteDAO discenteDAO, DiscenteRepository discenteRepository, PessoaRepository pessoaRepository) {
        this.pessoaDAO = pessoaDAO;
        this.discenteDAO = discenteDAO;
        this.discenteRepository = discenteRepository;
        this.pessoaRepository = pessoaRepository;
        this.contador = new AtomicInteger(discenteDAO.contarDiscentesCadastrados());
    }

    public String gerarNovoRegistroAcademico() {
        int anoAtual = Year.now().getValue();
        int numeroAtual = contador.incrementAndGet();

        String numeroFormatado = String.format("%03d", numeroAtual);

        return String.format("%d.1.08.%s", anoAtual, numeroFormatado);
    }

    public void adicionarDiscente(Pessoa pessoa, Discente discente) {
        Long pessoaId = pessoaDAO.inserirPessoa(pessoa);

        discente.setIdPessoa(pessoaId);

        discente.setRegistroAcademico(gerarNovoRegistroAcademico());

        discenteDAO.inserirDiscente(discente);
    }

    public Discente salvar(Discente discente) {
        discenteRepository.salvar(discente);
        return discente;
    }

    public List<Discente> listarTodos() {
        List<Discente> discentes = discenteRepository.listarTodos();

        for (Discente discente : discentes) {
            Optional<Pessoa> optionalPessoa = pessoaRepository.buscarPorId(discente.getIdPessoa());
            optionalPessoa.ifPresent(discente::setPessoa);
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
