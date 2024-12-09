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
    @Autowired
    private EnderecoDAO enderecoDAO;

    public Professor buscarPorId(Long id) {
        String sql = "SELECT * FROM professor WHERE ID_professor = ?";
        Professor professor = jdbcTemplate.queryForObject(sql, new ProfessorRowMapper(), id);
        if (professor != null) {
            Pessoa pessoa = pessoaDAO.buscarPessoaComEnderecos(professor.getIdPessoa());
            professor.setPessoa(pessoa);
        }
        return professor;
    }

    public void inserir(Professor professor) {
        String sql = "INSERT INTO professor (pessoa_ID, departamento_ID, data_contratacao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, professor.getIdPessoa(), professor.getIdDepartamento(), professor.getDataContratacao());
    }

    public void atualizar(Professor professor) {
        String sqlPr = "UPDATE professor SET departamento_ID = ? WHERE ID_professor = ?";
        jdbcTemplate.update(sqlPr, professor.getIdDepartamento(), professor.getIdPessoa());
    }

    public void remover(Long id) {
        String sql = "DELETE FROM professor WHERE ID_professor = ?";
        jdbcTemplate.update(sql, id);
    }
}
