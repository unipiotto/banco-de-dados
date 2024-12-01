package com.bancoDeDados.repository;

import com.bancoDeDados.model.Pessoa;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método para salvar ou atualizar uma pessoa
    public void salvar(Pessoa pessoa) {
        if (pessoa.getIdPessoa() == 0) {
            String sql = "INSERT INTO pessoa (nome, email, telefone, cpf, data_nascimento) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getCpf(), pessoa.getDataNascimento());
        } else {
            String sql = "UPDATE pessoa SET nome = ?, email = ?, telefone = ?, cpf = ?, data_nascimento = ? WHERE id_pessoa = ?";
            jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getCpf(), pessoa.getDataNascimento(), pessoa.getIdPessoa());
        }
    }

    public List<Pessoa> listarTodos() {
        String sql = "SELECT * FROM pessoa";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pessoa.class));
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        String sql = "SELECT * FROM pessoa WHERE id_pessoa = ?";
        try {
            Pessoa pessoa = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Pessoa.class));
            return Optional.of(pessoa);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    public void deletar(Long id) {
        String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";
        jdbcTemplate.update(sql, id);
    }
}
