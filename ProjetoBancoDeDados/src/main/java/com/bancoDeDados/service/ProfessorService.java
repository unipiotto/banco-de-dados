package com.bancoDeDados.service;

import com.bancoDeDados.model.*;
import com.bancoDeDados.repository.PessoaRepository;
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
    private final PessoaRepository pessoaRepository;

    @Autowired
    public ProfessorService(PessoaDAO pessoaDAO, ProfessorDAO professorDAO, EnderecoDAO enderecoDAO, ProfessorRepository professorRepository, PessoaRepository pessoaRepository) {
        this.pessoaDAO = pessoaDAO;
        this.professorDAO = professorDAO;
        this.enderecoDAO = enderecoDAO;
        this.professorRepository = professorRepository;
        this.pessoaRepository = pessoaRepository;

    }

    public void adicionarProfessor(Pessoa pessoa, List<Endereco> enderecos, Departamento departamento, Professor professor) {
        Long pessoaId = pessoaDAO.inserirPessoa(pessoa);
//        Long departamentoId = departamentoDAO.inserirDepartamento(departamento);

        for (Endereco endereco : enderecos) {
            endereco.setPessoaId(pessoaId);
            enderecoDAO.inserirEndereco(endereco);
        }

        professor.setPessoa(pessoa);
        professor.setDepartamento(departamento);

        professorDAO.inserir(professor);
    }


//    public Professor salvar(Professor professor) {
//        professorRepository.salvar(professor);
//        return professor;
//    }
//
//    public Optional<Professor> buscarPorId(Long id) {
//        return professorRepository.buscarPorId(id);
//    }
//
//    public void deletar(Long id) {
//        professorRepository.deletar(id);
//    }

}
