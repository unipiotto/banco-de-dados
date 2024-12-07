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

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    public void adicionarDiscente(DiscenteForm discenteForm) {

        // Construir objeto Pessoa
        Pessoa pessoa = Pessoa.builder()
                .nome(discenteForm.getNome())
                .email(discenteForm.getEmail())
                .telefone(discenteForm.getTelefone())
                .cpf(discenteForm.getCpf())
                .dataNascimento(discenteForm.getDataNascimento())
                .build();

        // Construir lista de Endereços
        List<Endereco> enderecos = discenteForm.getEnderecos().stream()
                .map(enderecoForm -> Endereco.builder()
                        .rua(enderecoForm.getRua())
                        .numero(enderecoForm.getNumero())
                        .complemento(enderecoForm.getComplemento())
                        .cidade(enderecoForm.getCidade())
                        .sigla(enderecoForm.getSiglaEstado())
                        .cep(enderecoForm.getCep())
                        .build())
                .collect(Collectors.toList());

        Discente discente = Discente.builder()
                .dataIngresso(LocalDate.now())
                .status(Discente.StatusDiscente.ATIVA)
                .build();

        Long pessoaId = pessoaDAO.inserirPessoa(pessoa);

        for (Endereco endereco : enderecos) {
            endereco.setPessoaId(pessoaId);
            enderecoDAO.inserirEndereco(endereco);
        }

        discente.setIdPessoa(pessoaId);

        discente.setRegistroAcademico(gerarNovoRegistroAcademico());

        discenteDAO.inserirDiscente(discente);
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

    public void editarDiscente(Long id, DiscenteForm discenteForm) throws Exception {
        Discente discenteOriginal = buscarDiscenteCompletoPorId(id);
        if (discenteOriginal == null) {
            throw new Exception("Discente não encontrado.");
        }

        // Atualiza informações de Pessoa
        Pessoa pessoaOriginal = discenteOriginal.getPessoa();
        pessoaOriginal.setNome(discenteForm.getNome());
        pessoaOriginal.setEmail(discenteForm.getEmail());
        pessoaOriginal.setTelefone(discenteForm.getTelefone());
        pessoaOriginal.setCpf(discenteForm.getCpf());
        pessoaOriginal.setDataNascimento(discenteForm.getDataNascimento());
        pessoaDAO.atualizarPessoa(pessoaOriginal);

        // Deleta os endereços antigos e insere os novos
        enderecoDAO.deletarPorPessoaId(pessoaOriginal.getIdPessoa());
        List<Endereco> enderecosAtualizados = discenteForm.getEnderecos().stream()
                .map(enderecoForm -> Endereco.builder()
                        .rua(enderecoForm.getRua())
                        .numero(enderecoForm.getNumero())
                        .complemento(enderecoForm.getComplemento())
                        .cidade(enderecoForm.getCidade())
                        .sigla(enderecoForm.getSiglaEstado())
                        .cep(enderecoForm.getCep())
                        .pessoaId(pessoaOriginal.getIdPessoa())
                        .build())
                .collect(Collectors.toList());

        for (Endereco endereco : enderecosAtualizados) {
            enderecoDAO.inserirEndereco(endereco);
        }
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
