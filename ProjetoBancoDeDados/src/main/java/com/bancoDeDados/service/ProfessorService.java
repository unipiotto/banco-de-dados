package com.bancoDeDados.service;

import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.model.dto.EnderecoForm;
import com.bancoDeDados.model.dto.ProfessorForm;
import com.bancoDeDados.repository.ProfessorRepository;
import com.bancoDeDados.repository.dao.EnderecoDAO;
import com.bancoDeDados.repository.dao.PessoaDAO;
import com.bancoDeDados.repository.dao.ProfessorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProfessorService {

    private final PessoaDAO pessoaDAO;
    private final ProfessorDAO professorDAO;
    private final EnderecoDAO enderecoDAO;
    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(PessoaDAO pessoaDAO, ProfessorDAO professorDAO, EnderecoDAO enderecoDAO, ProfessorRepository professorRepository) {
        this.pessoaDAO = pessoaDAO;
        this.professorDAO = professorDAO;
        this.enderecoDAO = enderecoDAO;
        this.professorRepository = professorRepository;

    }

    public void adicionar(ProfessorForm professorForm) {


        Pessoa pessoa = Pessoa.builder()
                .nome(professorForm.getNome())
                .email(professorForm.getEmail())
                .telefone(professorForm.getTelefone())
                .cpf(professorForm.getCpf())
                .dataNascimento(professorForm.getDataNascimento())
                .build();

        Long departamentoId = professorForm.getDepartamentoId();

        List<Endereco> enderecos = professorForm.getEnderecos().stream()
                .map(enderecoForm -> Endereco.builder()
                        .rua(enderecoForm.getRua())
                        .numero(enderecoForm.getNumero())
                        .complemento(enderecoForm.getComplemento())
                        .cidade(enderecoForm.getCidade())
                        .sigla(enderecoForm.getSiglaEstado())
                        .cep(enderecoForm.getCep())
                        .build())
                .toList();


        Long pessoaId = pessoaDAO.inserirPessoa(pessoa);

        for (Endereco endereco : enderecos) {
            endereco.setPessoaId(pessoaId);
            enderecoDAO.inserirEndereco(endereco);
        }


        Professor professor = Professor.builder()
                .dataContratacao(LocalDate.now())
                .build();

//        professor.setPessoa(pessoa);
        professor.setIdPessoa(pessoaId);

        professor.setIdDepartamento(departamentoId);

        professorDAO.inserir(professor);
    }

    public void editarProfessor(Long id, ProfessorForm professorForm) throws Exception {
        Professor professorOriginal = buscarPorId(id);
        if (professorOriginal == null) {
            throw new Exception("Professor não encontrado.");
        }

        professorOriginal.setIdDepartamento(professorForm.getDepartamentoId());

        // Atualiza informações de Pessoa
        Pessoa pessoaOriginal = professorOriginal.getPessoa();
        pessoaOriginal.setNome(professorForm.getNome());
        pessoaOriginal.setEmail(professorForm.getEmail());
        pessoaOriginal.setTelefone(professorForm.getTelefone());
        pessoaOriginal.setCpf(professorForm.getCpf());
        pessoaOriginal.setDataNascimento(professorForm.getDataNascimento());
        pessoaDAO.atualizarPessoa(pessoaOriginal);

        // Deleta os endereços antigos e insere os novos
        enderecoDAO.deletarPorPessoaId(pessoaOriginal.getIdPessoa());
        List<Endereco> enderecosAtualizados = professorForm.getEnderecos().stream()
                .map(enderecoForm -> Endereco.builder()
                        .rua(enderecoForm.getRua())
                        .numero(enderecoForm.getNumero())
                        .complemento(enderecoForm.getComplemento())
                        .cidade(enderecoForm.getCidade())
                        .sigla(enderecoForm.getSiglaEstado())
                        .cep(enderecoForm.getCep())
                        .pessoaId(pessoaOriginal.getIdPessoa())
                        .build())
                .toList();

        for (Endereco endereco : enderecosAtualizados) {
            enderecoDAO.inserirEndereco(endereco);
        }
        professorDAO.atualizar(professorOriginal);
    }

    public List<Professor> listar() {
        return professorRepository.listar();
    }

    public Professor buscarPorId(Long id){
        return professorDAO.buscarPorId(id);
    }

    public ProfessorForm converterProfessorParaForm(Professor professor) {
        if (professor == null || professor.getPessoa() == null) {
            throw new IllegalArgumentException("Professor ou Pessoa não podem ser nulos");
        }

        Pessoa pessoa = professor.getPessoa();

        ProfessorForm form = new ProfessorForm();
        form.setNome(pessoa.getNome());
        form.setEmail(pessoa.getEmail());
        form.setCpf(pessoa.getCpf());
        form.setTelefone(pessoa.getTelefone());
        form.setDataNascimento(pessoa.getDataNascimento());

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
