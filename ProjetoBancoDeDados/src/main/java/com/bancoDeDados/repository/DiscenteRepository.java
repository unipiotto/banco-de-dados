package com.bancoDeDados.repository;

import com.bancoDeDados.model.Discente;
import com.bancoDeDados.model.Discente.StatusDiscente;
import com.bancoDeDados.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiscenteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void salvar(Discente discente) {
        if (discente.getIdDiscente() == null) {
            String sql = "INSERT INTO discente (registro_academico, data_ingresso, status) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql,
                    discente.getRegistroAcademico(),
                    discente.getDataIngresso(),
                    discente.getStatus().name());
        } else {
            String sql = "UPDATE discente SET registro_academico = ?, data_ingresso = ?, status = ? WHERE ID_discente = ?";
            jdbcTemplate.update(sql,
                    discente.getRegistroAcademico(),
                    discente.getDataIngresso(),
                    discente.getStatus().name(),
                    discente.getIdDiscente());
        }
    }

    public List<Discente> listarTodos() {
        String sql = """
        SELECT d.ID_discente, d.registro_academico, d.data_ingresso, d.status, 
               p.ID_pessoa, p.nome, p.email, p.telefone, p.cpf, p.data_nascimento
        FROM discente d
        JOIN pessoa p ON d.pessoa_ID = p.ID_pessoa
    """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Discente discente = new Discente();
            discente.setIdDiscente(rs.getLong("ID_discente"));
            discente.setRegistroAcademico(rs.getString("registro_academico"));
            discente.setDataIngresso(rs.getDate("data_ingresso").toLocalDate());
            discente.setStatus(StatusDiscente.valueOf(rs.getString("status").toUpperCase()));

            Pessoa pessoa = new Pessoa();
            pessoa.setIdPessoa(rs.getLong("ID_pessoa"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setTelefone(rs.getString("telefone"));
            pessoa.setCpf(rs.getString("cpf"));
            pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
            discente.setPessoa(pessoa);

            return discente;
        });
    }


    public Optional<Discente> buscarPorId(Long id) {
        String sql = "SELECT * FROM discente WHERE ID_discente = ?";
        try {
            Discente discente = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Discente d = new Discente();
                d.setIdDiscente(rs.getLong("ID_discente"));
                d.setRegistroAcademico(rs.getString("registro_academico"));
                d.setDataIngresso(rs.getDate("data_ingresso").toLocalDate());
                d.setStatus(StatusDiscente.valueOf(rs.getString("Status").toUpperCase()));
                return d;
            });
            return Optional.of(discente);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM discente WHERE ID_Discente = ?";
        jdbcTemplate.update(sql, id);
    }
}
