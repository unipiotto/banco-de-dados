package com.bancoDeDados.service;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.dto.DiscenteForm;
import com.bancoDeDados.model.dto.EnderecoForm;
import com.bancoDeDados.repository.dao.DiscenteDAO;
import com.bancoDeDados.repository.dao.EnderecoDAO;
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
    private final EnderecoDAO enderecoDAO;
    private final DiscenteRepository discenteRepository;
    private final PessoaRepository pessoaRepository;
    private final AtomicInteger contador;

    @Autowired
    public DiscenteService(PessoaDAO pessoaDAO, DiscenteDAO discenteDAO, EnderecoDAO enderecoDAO, DiscenteRepository discenteRepository, PessoaRepository pessoaRepository) {
        this.pessoaDAO = pessoaDAO;
        this.discenteDAO = discenteDAO;
        this.enderecoDAO = enderecoDAO;
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

    public void adicionarDiscente(Pessoa pessoa, List<Endereco> enderecos, Discente discente) {
        Long pessoaId = pessoaDAO.inserirPessoa(pessoa);

        for (Endereco endereco : enderecos) {
            endereco.setPessoaId(pessoaId);
            enderecoDAO.inserirEndereco(endereco);
        }

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

    public Discente buscarDiscenteCompletoPorId(Long id) {
        return discenteDAO.buscarDiscenteCompletoPorId(id);
    }

    public void deletar(Long id) {
        discenteRepository.deletar(id);
    }

    public DiscenteForm converterDiscenteParaForm(Discente discente) {
        if (discente == null || discente.getPessoa() == null) {
            throw new IllegalArgumentException("Discente ou Pessoa não podem ser nulos");
        }

        Pessoa pessoa = discente.getPessoa();

        DiscenteForm form = new DiscenteForm();
        form.setNome(pessoa.getNome());
        form.setEmail(pessoa.getEmail());
        form.setTelefone(pessoa.getTelefone());
        form.setDataNascimento(pessoa.getDataNascimento());
        form.setCpf(pessoa.getCpf());

        form.setMatricula(discente.getRegistroAcademico());
        form.setCurso(null);

        List<EnderecoForm> enderecosForm = pessoa.getEnderecos().stream()
                .map(this::converterEnderecoParaForm)
                .toList();
        form.setEnderecos(enderecosForm);

        return form;
    }

    private EnderecoForm converterEnderecoParaForm(Endereco endereco) {
        EnderecoForm enderecoForm = new EnderecoForm();
        enderecoForm.setRua(endereco.getRua());
        enderecoForm.setNumero(endereco.getNumero());
        enderecoForm.setComplemento(endereco.getComplemento());
        enderecoForm.setCidade(endereco.getCidade());
        enderecoForm.setSiglaEstado(endereco.getSiglaEstadoEnum());
        enderecoForm.setCep(endereco.getCep());
        return enderecoForm;
    }
}
