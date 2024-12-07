package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Pessoa;
import com.bancoDeDados.model.Professor;
import com.bancoDeDados.model.mapper.ProfessorRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProfessorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PessoaDAO pessoaDAO;

    public Professor buscarPorId(Long id) {
        String sql = "SELECT * FROM professores WHERE ID_professor = ?";
        Professor professor = jdbcTemplate.queryForObject(sql, new ProfessorRowMapper(), id);
        if (professor != null) {
            Pessoa pessoa = pessoaDAO.buscarPessoaComEnderecos(professor.getIdPessoa());
            professor.setPessoa(pessoa);
        }
        return professor;
    }

    public void inserir(Professor professor) {
        String sql = "INSERT INTO professores (pessoa_ID, departamento_ID, data_contratacao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, professor.getIdPessoa(), professor.getIdDepartamento(), professor.getDataContratacao());
    }

    public void atualizar(Professor professor) {
        String sql = "UPDATE professores SET departamento_ID = ?, data_contratacao = ? WHERE ID_professor = ?";
        jdbcTemplate.update(sql, professor.getIdDepartamento(), professor.getDataContratacao(), professor.getIdPessoa());
    }

    public void remover(Professor professor) {
        String sql = "DELETE FROM professores WHERE ID_professor = ?";
        jdbcTemplate.update(sql, professor.getIdPessoa());
    }
}
