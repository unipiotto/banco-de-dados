package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Avaliacao;
import com.bancoDeDados.model.mapper.AvaliacaoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AvaliacaoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void inserir(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacoes (matricula_ID, nota, peso) values (?, ?, ?)";
        jdbcTemplate.update(sql, avaliacao.getIdMatricula(), avaliacao.getNota(), avaliacao.getPeso());
    }

    public void atualizar(Avaliacao avaliacao) {
        String sql = "UPDATE avaliacoes SET nota = ?, peso = ?, matricula_ID = ? WHERE ID_avaliacao = ?";
        jdbcTemplate.update(sql, avaliacao.getNota(), avaliacao.getPeso(), avaliacao.getIdMatricula(), avaliacao.getIdAvaliacao());
    }


    public Avaliacao buscarPorId(Long idAvaliacao) {
        String sql = "SELECT * FROM avaliacoes WHERE ID_avaliacao = ?";
        return jdbcTemplate.queryForObject(sql, new AvaliacaoRowMapper(), idAvaliacao);
    }
}
