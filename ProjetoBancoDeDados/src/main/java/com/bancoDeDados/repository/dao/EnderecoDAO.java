package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.mapper.EnderecoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnderecoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Endereco> listarEnderecosPorPessoaId(Long pessoaId) {
        String sql = "SELECT * FROM endereco WHERE pessoa_id = ?";
        return jdbcTemplate.query(sql, new EnderecoRowMapper(), pessoaId);
    }
}
