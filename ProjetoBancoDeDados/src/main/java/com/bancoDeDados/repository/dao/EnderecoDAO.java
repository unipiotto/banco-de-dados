package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.mapper.EnderecoRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class EnderecoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Endereco> listarEnderecosPorPessoaId(Long pessoaId) {
        String sql = "SELECT * FROM endereco WHERE pessoa_id = ?";
        return jdbcTemplate.query(sql, new EnderecoRowMapper(), pessoaId);
    }

    public Long inserirEndereco(Endereco endereco) {
        String sql = "INSERT INTO endereco (pessoa_id, rua, numero, cep, complemento, sigla_estado, cidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id_endereco" });
            ps.setLong(1, endereco.getPessoaId());
            ps.setString(2, endereco.getRua());
            ps.setString(3, endereco.getNumero());
            ps.setString(4, endereco.getCep());
            ps.setString(5, endereco.getComplemento());
            ps.setString(6, endereco.getSiglaEstado());
            ps.setString(7, endereco.getCidade());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
