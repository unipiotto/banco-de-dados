package com.bancoDeDados.repository;

import com.bancoDeDados.model.entities.Discente;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiscenteRepository {

    private JdbcTemplate jdbcTemplate;

    public void salvar(Discente discente) {
        if (discente.getIdDiscente() == null) {
            String sql = "INSERT INTO discente (registroAcademico, dataIngresso, status) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, discente.getRegistroAcademico(), discente.getDataIngresso(), discente.getStatus());
        } else {
            String sql = "UPDATE discente SET registroAcademico = ?, dataIngresso = ?, status = ? WHERE ID_discente = ?";
            jdbcTemplate.update(sql, discente.getRegistroAcademico(), discente.getDataIngresso(), discente.getStatus(), discente.getIdDiscente());
        }
    }

    public List<Discente> listarTodos() {
        String sql = "SELECT * FROM discente";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Discente.class));
    }

    public Optional<Discente> buscarPorId(Long id) {
        String sql = "SELECT * FROM discente WHERE ID_discente = ?";
        try {
            Discente discente = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Discente.class));
            return Optional.of(discente);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM discente WHERE ID_discente = ?";
        jdbcTemplate.update(sql, id);
    }
}
