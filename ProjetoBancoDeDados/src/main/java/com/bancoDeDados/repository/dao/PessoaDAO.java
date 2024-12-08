package com.bancoDeDados.repository.dao;

import com.bancoDeDados.model.Endereco;
import com.bancoDeDados.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PessoaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EnderecoDAO enderecoDAO;

    public Pessoa buscarPessoaComEnderecos(Long pessoaId) {
        String sql = "SELECT * FROM pessoa WHERE id_pessoa = ?";
        Pessoa pessoa = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pessoa.class), pessoaId);
        if (pessoa != null) {
            List<Endereco> enderecos = enderecoDAO.listarEnderecosPorPessoaId(pessoaId);
            pessoa.setEnderecos(enderecos);
        }
        return pessoa;
    }

    public Long inserirPessoa(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, email, telefone, cpf, data_nascimento) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id_pessoa"});
            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getEmail());
            ps.setString(3, pessoa.getTelefone());
            ps.setString(4, pessoa.getCpf());
            LocalDate dataNascimento = pessoa.getDataNascimento();
            ps.setDate(5, Date.valueOf(dataNascimento));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void atualizarPessoa(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, email = ?, telefone = ?, cpf = ? WHERE ID_pessoa = ?";
        jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(),
                pessoa.getCpf(), pessoa.getIdPessoa());
    }
}